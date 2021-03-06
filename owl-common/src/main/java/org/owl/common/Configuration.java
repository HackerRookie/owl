package org.owl.common;

import java.io.File;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Configuration {

	/*-------------------------------------------------------*/
	/** Directories normally at the top of the game hierarchy*/
	 /** The default configuration directory. */
    private static final String DEFAULT_DIR_NAME_CONFIG = "mmconf";

    /** The default data directory. */
    private static final String DEFAULT_DIR_NAME_DATA = "data";

    /** The default documentation directory. */
    private static final String DEFAULT_DIR_NAME_DOCS = "docs";
    /*-------------------------------------------------------*/
    
    //these are all directories that normally appear under 'data'
    /**
     * The default random army tables directory name (under the data directory).
     */
    private static final String DEFAULT_DIR_NAME_ARMY_TABLES = "rat";

    /** The default boards directory name (under the data directory). */
    private static final String DEFAULT_DIR_NAME_BOARDS = "boards";

    /** The default images directory name (under the data directory). */
    private static final String DEFAULT_DIR_NAME_IMAGES = "images";

    /** The default random names directory (under the data directory). */
    private static final String DEFAULT_DIR_NAME_NAMES = "names";

    /** The default unit files directory name (under the data directory). */
    private static final String DEFAULT_DIR_NAME_UNITS = "mechfiles";

    /** The default scenarios directory name (under the data directory). */
    private static final String DEFAULT_DIR_NAME_SCENARIOS = "scenarios";

    /** The default sounds directory name (under the data directory). */
    private static final String DEFAULT_DIR_NAME_SOUNDS = "sounds";

    // **************************************************************************
    // These are all directories that normally appear under 'data/images'.

    /** The default camo directory name (under the images directory). */
    private static final String DEFAULT_DIR_NAME_CAMO = "camo";

    /** The default fluff images directory name (under the images directory). */
    private static final String DEFAULT_DIR_NAME_FLUFF_IMAGES = "fluff";

    /** The default hex images directory name (under the images directory). */
    private static final String DEFAULT_DIR_NAME_HEXES = "hexes";

    /** The default misc images directory name (under the images directory). */
    private static final String DEFAULT_DIR_NAME_MISC_IMAGES = "misc";

    /** The default portrait images directory name (under the images directory). */
    private static final String DEFAULT_DIR_NAME_PORTRAIT_IMAGES = "portraits";

    /** The default unit images directory name (under the images directory). */
    private static final String DEFAULT_DIR_NAME_UNIT_IMAGES = "units";

    /** The default widgets directory name (under the images directory). */
    private static final String DEFAULT_DIR_NAME_WIDGETS = "widgets";
    // **************************************************************************
    
    private static ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    
    /** The configured configuration directory. */
    private static File config_dir = new File(DEFAULT_DIR_NAME_CONFIG);

    /** The configured data directory. */
    private static File data_dir = new File(DEFAULT_DIR_NAME_DATA);

    /** The configured documentation directory. */
    private static File docs_dir = new File(DEFAULT_DIR_NAME_DOCS);

    /** The configured army tables directory. */
    private static File army_tables_dir = null;

    /** The configured boards directory. */
    private static File boards_dir = null;

    /** The configured images directory. */
    private static File images_dir = null;

    /** The configured unit files directory. */
    private static File units_dir = null;

    /** The configured names directory. */
    private static File names_dir = null;

    /** The configured scenarios directory. */
    private static File scenarios_dir = null;

    /** The configured sounds directory. */
    private static File sounds_dir = null;
    
    // **************************************************************************
    // Static methods for accessing and modifying configuration data.

    /**
     * Return the configured configuration file directory.
     * 
     * @return {@link File} containing the path to the config directory.
     */
    public static File configDir() {
        lock.readLock().lock();
        try {
            return config_dir;
        } finally {
            lock.readLock().unlock();
        }
    }

    /**
     * Set the configuration directory.
     * 
     * @param config_dir_path
     *            The path to the config directory.
     */
    public static void setConfigDir(final File config_dir_path) {
        lock.writeLock().lock();
        config_dir = (config_dir_path == null) ? new File(
                DEFAULT_DIR_NAME_CONFIG) : config_dir_path;
        lock.writeLock().unlock();
    }

    /**
     * Return the configured data directory.
     * 
     * @return {@link File} containing the path to the data directory.
     */
    public static File dataDir() {
        lock.readLock().lock();
        try {
            return data_dir;
        } finally {
            lock.readLock().unlock();
        }
    }

    /**
     * Set the data directory.
     * 
     * @param data_dir_path
     *            The path to the data directory.
     */
    public static void setDataDir(final File data_dir_path) {
        lock.writeLock().lock();
        data_dir = (data_dir_path == null) ? new File(DEFAULT_DIR_NAME_DATA)
                : data_dir_path;
        lock.writeLock().unlock();
    }

    /**
     * Return the configured documentation directory.
     * 
     * @return {@link File} containing the path to the documentation directory.
     */
    public static File docsDir() {
        lock.readLock().lock();
        try {
            return docs_dir;
        } finally {
            lock.readLock().unlock();
        }
    }

    /**
     * Set the documentation directory.
     * 
     * @param docs_dir_path
     *            The path to the documentation directory.
     */
    public static void setDocsDir(final File docs_dir_path) {
        lock.writeLock().lock();
        docs_dir = (docs_dir_path == null) ? new File(DEFAULT_DIR_NAME_DOCS)
                : docs_dir_path;
        lock.writeLock().unlock();
    }

    /**
     * Return the configured army tables directory, if set, otherwise return the
     * default path, relative to the configured data directory.
     * 
     * @return {@link File} containing the path to the army tables directory.
     */
    public static File armyTablesDir() {
        lock.readLock().lock();
        try {
            return (army_tables_dir != null) ? army_tables_dir : new File(
                    dataDir(), DEFAULT_DIR_NAME_ARMY_TABLES);
        } finally {
            lock.readLock().unlock();
        }
    }

    /**
     * Set the army tables directory to an arbitrary location (<b>not</b>
     * relative to the data directory).
     * 
     * @param army_tables_dir_path
     *            The path to the army tables directory.
     */
    public static void setArmyTablesDir(final File army_tables_dir_path) {
        lock.writeLock().lock();
        army_tables_dir = army_tables_dir_path;
        lock.writeLock().unlock();
    }

    /**
     * Return the configured boards directory, if set, otherwise return the
     * default path, relative to the configured data directory.
     * 
     * @return {@link File} containing the path to the boards directory.
     */
    public static File boardsDir() {
        lock.readLock().lock();
        try {
            return (boards_dir != null) ? boards_dir : new File(dataDir(),
                    DEFAULT_DIR_NAME_BOARDS);
        } finally {
            lock.readLock().unlock();
        }
    }

    /**
     * Set the boards directory to an arbitrary location (<b>not</b> relative to
     * the data directory).
     * 
     * @param boards
     *            dir path The path to the boards directory.
     */
    public static void setBoardsDir(final File boards_dir_path) {
        lock.writeLock().lock();
        boards_dir = boards_dir_path;
        lock.writeLock().unlock();
    }

    /**
     * Return the camo directory, which is relative to the images directory.
     * 
     * @return {@link File} containing the path to the camo directory.
     */
    public static File camoDir() {
        return new File(imagesDir(), DEFAULT_DIR_NAME_CAMO);
    }

    /**
     * Return the hexes directory, which is relative to the images directory.
     * 
     * @return {@link File} containing the path to the camo directory.
     */
    public static File hexesDir() {
        return new File(imagesDir(), DEFAULT_DIR_NAME_HEXES);
    }

    /**
     * Get the fluff images directory, which is relative to the images
     * directory.
     * 
     * @return {@link File} containing the path to the fluff images directory.
     */
    public static File fluffImagesDir() {
        return new File(imagesDir(), DEFAULT_DIR_NAME_FLUFF_IMAGES);
    }

    /**
     * Return the configured images directory, if set, otherwise return the
     * default path, relative to the configured data directory.
     * 
     * @return {@link File} containing the path to the images directory.
     */
    public static File imagesDir() {
        lock.readLock().lock();
        try {
            return (images_dir != null) ? images_dir : new File(dataDir(),
                    DEFAULT_DIR_NAME_IMAGES);
        } finally {
            lock.readLock().unlock();
        }
    }

    /**
     * Set the images directory to an arbitrary location (<b>not</b> relative to
     * the data directory).
     * 
     * @param images_dir_path
     *            The path to the images directory.
     */
    public static void setImagesDir(final File images_dir_path) {
        lock.writeLock().lock();
        images_dir = images_dir_path;
        lock.writeLock().unlock();
    }

    /**
     * Return the configured units directory, if set, otherwise return the
     * default path, relative to the configured data directory.
     * 
     * @return {@link File} containing the path to the units directory.
     */
    public static File unitsDir() {
        lock.readLock().lock();
        try {
            return (units_dir != null) ? units_dir : new File(dataDir(),
                    DEFAULT_DIR_NAME_UNITS);
        } finally {
            lock.readLock().unlock();
        }
    }

    /**
     * Set the units directory to an arbitrary location (<b>not</b> relative to
     * the data directory).
     * 
     * @param units_dir_path
     *            The path to the units directory.
     */
    public static void setUnitsDir(final File units_dir_path) {
        lock.writeLock().lock();
        units_dir = units_dir_path;
        lock.writeLock().unlock();
    }

    /**
     * Return the misc images directory, which is relative to the images
     * directory.
     * 
     * @return {@link File} containing the path to the misc directory.
     */
    public static File miscImagesDir() {
        return new File(imagesDir(), DEFAULT_DIR_NAME_MISC_IMAGES);
    }

    /**
     * Return the portrait images directory, which is relative to the images
     * directory.
     * 
     * @return {@link File} containing the path to the portrait directory.
     */
    public static File portraitImagesDir() {
        return new File(imagesDir(), DEFAULT_DIR_NAME_PORTRAIT_IMAGES);
    }

    /**
     * Return the configured names directory, if set, otherwise return the
     * default path, relative to the configured data directory.
     * 
     * @return {@link File} containing the path to the names directory.
     */
    public static File namesDir() {
        lock.readLock().lock();
        try {
            return (names_dir != null) ? names_dir : new File(dataDir(),
                    DEFAULT_DIR_NAME_NAMES);
        } finally {
            lock.readLock().unlock();
        }
    }

    /**
     * Set the names directory to an arbitrary location (<b>not</b> relative to
     * the data directory).
     * 
     * @param names_dir_path
     *            The path to the names directory.
     */
    public static void setNamesDir(final File names_dir_path) {
        lock.writeLock().lock();
        names_dir = names_dir_path;
        lock.writeLock().unlock();
    }

    /**
     * Return the configured scenarios directory, if set, otherwise return the
     * default path, relative to the configured data directory.
     * 
     * @return {@link File} containing the path to the scenarios directory.
     */
    public static File scenariosDir() {
        lock.readLock().lock();
        try {
            return (scenarios_dir != null) ? scenarios_dir : new File(
                    dataDir(), DEFAULT_DIR_NAME_SCENARIOS);
        } finally {
            lock.readLock().unlock();
        }
    }

    /**
     * Set the scenarios directory to an arbitrary location (<b>not</b> relative
     * to the data directory).
     * 
     * @param scenarios_dir_path
     *            The path to the scenarios directory.
     */
    public static void setScenariosDir(final File scenarios_dir_path) {
        lock.writeLock().lock();
        scenarios_dir = scenarios_dir_path;
        lock.writeLock().unlock();
    }

    /**
     * Return the configured sounds directory, if set, otherwise return the
     * default path, relative to the configured data directory.
     * 
     * @return {@link File} containing the path to the sounds directory.
     */
    public static File soundsDir() {
        lock.readLock().lock();
        try {
            return (sounds_dir != null) ? sounds_dir : new File(dataDir(),
                    DEFAULT_DIR_NAME_SOUNDS);
        } finally {
            lock.readLock().unlock();
        }
    }

    /**
     * Set the sounds directory to an arbitrary location (<b>not</b> relative to
     * the data directory).
     * 
     * @param sounds_dir_path
     *            The path to the sounds directory.
     */
    public static void setSoundsDir(final File sounds_dir_path) {
        lock.writeLock().lock();
        sounds_dir = sounds_dir_path;
        lock.writeLock().unlock();
    }

    /**
     * Get the unit images directory, which is relative to the images directory.
     * 
     * @return {@link File} containing the path to the unit images directory.
     */
    public static File unitImagesDir() {
        return new File(imagesDir(), DEFAULT_DIR_NAME_UNIT_IMAGES);
    }

    /**
     * Get the widgets directory, which is relative to the images directory.
     * 
     * @return {@link File} containing the path to the widgets directory.
     */
    public static File widgetsDir() {
        return new File(imagesDir(), DEFAULT_DIR_NAME_WIDGETS);
    }

    // **************************************************************************
}
