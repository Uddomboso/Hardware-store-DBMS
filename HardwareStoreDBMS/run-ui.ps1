# PowerShell script to run the Hardware Store DBMS UI
# This script downloads MySQL connector if needed and runs the GUI

$ErrorActionPreference = "Stop"

# Configuration
$LIB_DIR = "lib"

# Get the script directory
$ScriptDir = Split-Path -Parent $MyInvocation.MyCommand.Path
Set-Location $ScriptDir

# Create lib directory if it doesn't exist
if (-not (Test-Path $LIB_DIR)) {
    New-Item -ItemType Directory -Path $LIB_DIR | Out-Null
}

# Find MySQL connector JAR (try common names)
$ConnectorPath = $null
$PossibleNames = @("mysql-connector-j-8.0.33.jar", "mysql-connector-java-8.0.33.jar", "mysql-connector-j*.jar")
foreach ($name in $PossibleNames) {
    $found = Get-ChildItem -Path $LIB_DIR -Filter $name -ErrorAction SilentlyContinue | Select-Object -First 1
    if ($found) {
        $ConnectorPath = $found.FullName
        break
    }
}

# Download MySQL connector if it doesn't exist
if (-not $ConnectorPath) {
    Write-Host "Downloading MySQL Connector..." -ForegroundColor Yellow
    $ConnectorPath = Join-Path $LIB_DIR "mysql-connector-j-8.0.33.jar"
    try {
        $url = "https://repo1.maven.org/maven2/com/mysql/mysql-connector-j/8.0.33/mysql-connector-j-8.0.33.jar"
        Invoke-WebRequest -Uri $url -OutFile $ConnectorPath -ErrorAction Stop
        Write-Host "MySQL Connector downloaded successfully!" -ForegroundColor Green
    } catch {
        Write-Host "Error downloading MySQL Connector: $_" -ForegroundColor Red
        Write-Host "Please download it manually and place in: $LIB_DIR" -ForegroundColor Yellow
        exit 1
    }
}

# Build classpath
$ClassPath = "target/classes;$ConnectorPath"

# Check which GUI to run (default: ProductF)
$GuiClass = $args[0]
if ([string]::IsNullOrEmpty($GuiClass)) {
    $GuiClass = "ProductF"
}

$FullClassName = "com.mycompany.hardwarestoredbms.GUI.$GuiClass"

Write-Host "Running $FullClassName..." -ForegroundColor Cyan
Write-Host "Classpath: $ClassPath" -ForegroundColor Gray

# Run the GUI
try {
    java -cp $ClassPath $FullClassName
} catch {
    Write-Host "Error running GUI: $_" -ForegroundColor Red
    exit 1
}
