package com.mycompany.hardwarestoredbms;

import java.io.*;
import java.nio.file.*;
import java.util.*;

/**
 * Configuration utility class for reading environment variables from .env file
 */
public class Config {
    private static final Map<String, String> config = new HashMap<>();
    private static boolean loaded = false;
    
    // Default values (fallback if .env file is not found)
    private static final String DEFAULT_DB_HOST = "localhost";
    private static final String DEFAULT_DB_PORT = "3306";
    private static final String DEFAULT_DB_NAME = "hardwaredb";
    private static final String DEFAULT_DB_USER = "root";
    private static final String DEFAULT_DB_PASSWORD = "29102001";
    
    /**
     * Load configuration from .env file
     */
    public static void load() {
        if (loaded) return;
        
        try {
            Path envPath = findEnvFile();
            
            if (envPath != null && Files.exists(envPath)) {
                loadFromFile(envPath);
                System.out.println("Configuration loaded from: " + envPath.toAbsolutePath());
            } else {
                System.out.println("Warning: .env file not found. Using default configuration.");
                loadDefaults();
            }
        } catch (Exception e) {
            System.err.println("Error loading configuration: " + e.getMessage());
            loadDefaults();
        }
        
        loaded = true;
    }
    
    /**
     * Find .env file in various possible locations
     */
    private static Path findEnvFile() {
        // List of possible locations for .env file
        String[] possiblePaths = {
            ".env",                                    // Current directory
            "HardwareStoreDBMS/.env",                  // Project subdirectory
            "../.env",                                 // Parent directory
            "../HardwareStoreDBMS/.env",               // Parent/Project
            System.getProperty("user.dir") + "/.env",   // User directory
            System.getProperty("user.dir") + "/HardwareStoreDBMS/.env"
        };
        
        // Also try to find relative to class location
        try {
            String classPath = Config.class.getProtectionDomain()
                .getCodeSource().getLocation().getPath();
            if (classPath != null && !classPath.isEmpty()) {
                Path classDir = Paths.get(classPath).getParent();
                if (classDir != null) {
                    // Try going up directories to find .env
                    Path current = classDir;
                    for (int i = 0; i < 5; i++) {
                        Path envFile = current.resolve(".env");
                        if (Files.exists(envFile)) {
                            return envFile;
                        }
                        Path parent = current.getParent();
                        if (parent == null) break;
                        current = parent;
                    }
                }
            }
        } catch (Exception e) {
            // Ignore errors in class path detection
        }
        
        // Try all possible paths
        for (String pathStr : possiblePaths) {
            try {
                Path path = Paths.get(pathStr);
                if (Files.exists(path)) {
                    return path;
                }
            } catch (Exception e) {
                // Continue to next path
            }
        }
        
        return null;
    }
    
    /**
     * Load configuration from .env file
     */
    private static void loadFromFile(Path envPath) throws IOException {
        List<String> lines = Files.readAllLines(envPath);
        
        for (String line : lines) {
            line = line.trim();
            
            // Skip empty lines and comments
            if (line.isEmpty() || line.startsWith("#")) {
                continue;
            }
            
            // Parse KEY=VALUE format
            int equalsIndex = line.indexOf('=');
            if (equalsIndex > 0) {
                String key = line.substring(0, equalsIndex).trim();
                String value = line.substring(equalsIndex + 1).trim();
                
                // Remove quotes if present
                if ((value.startsWith("\"") && value.endsWith("\"")) ||
                    (value.startsWith("'") && value.endsWith("'"))) {
                    value = value.substring(1, value.length() - 1);
                }
                
                config.put(key, value);
            }
        }
    }
    
    /**
     * Load default configuration values
     */
    private static void loadDefaults() {
        config.put("DB_HOST", DEFAULT_DB_HOST);
        config.put("DB_PORT", DEFAULT_DB_PORT);
        config.put("DB_NAME", DEFAULT_DB_NAME);
        config.put("DB_USER", DEFAULT_DB_USER);
        config.put("DB_PASSWORD", DEFAULT_DB_PASSWORD);
    }
    
    /**
     * Get configuration value by key
     */
    public static String get(String key) {
        if (!loaded) {
            load();
        }
        return config.getOrDefault(key, "");
    }
    
    /**
     * Get configuration value with default fallback
     */
    public static String get(String key, String defaultValue) {
        if (!loaded) {
            load();
        }
        return config.getOrDefault(key, defaultValue);
    }
    
    /**
     * Get database host
     */
    public static String getDbHost() {
        return get("DB_HOST", DEFAULT_DB_HOST);
    }
    
    /**
     * Get database port
     */
    public static String getDbPort() {
        return get("DB_PORT", DEFAULT_DB_PORT);
    }
    
    /**
     * Get database name
     */
    public static String getDbName() {
        return get("DB_NAME", DEFAULT_DB_NAME);
    }
    
    /**
     * Get database username
     */
    public static String getDbUser() {
        return get("DB_USER", DEFAULT_DB_USER);
    }
    
    /**
     * Get database password
     */
    public static String getDbPassword() {
        return get("DB_PASSWORD", DEFAULT_DB_PASSWORD);
    }
    
    /**
     * Get complete database URL
     */
    public static String getDbUrl() {
        return String.format("jdbc:mysql://%s:%s/%s", 
            getDbHost(), getDbPort(), getDbName());
    }
}
