/**
 * 
 */
package org.owl.common.preference;

import java.io.File;
import java.io.PrintWriter;
import java.util.Locale;

/**
 * @author cy
 *
 */
public class ClientPreferences extends PreferenceProxy implements
		IClientPreferences {

	ClientPreferences(IPreference store){
		this.preference = store;
		store.setDefault(LAST_CONNECT_ADDR, "localhost");
        store.setDefault(LAST_CONNECT_PORT, 2346);
        store.setDefault(LAST_SERVER_PORT, 2346);
        store.setDefault(MAP_TILESET, "atmospheric.tileset");
//        store.setDefault(MAX_PATHFINDER_TIME,MovePath.DEFAULT_PATHFINDER_TIME_LIMIT);
        store.setDefault(DATA_DIRECTORY, "data");
        store.setDefault(LOG_DIRECTORY, "logs");
        store.setDefault(MECH_DIRECTORY, store.getDefaultString(DATA_DIRECTORY)
                + File.separator + "mechfiles");
        store.setDefault(METASERVER_NAME,
                "http://stormy-light-2818.herokuapp.com/announce");
        store.setDefault(GAMELOG_KEEP, true);
        store.setDefault(GAMELOG_FILENAME, "gamelog.html");
        // store.setDefault(GAMELOG_MAX_SIZE, 1);
        store.setDefault(STAMP_FORMAT, "_yyyy-MM-dd_HH-mm-ss");
        store.setDefault(UNIT_START_CHAR, 'A');
        store.setDefault(GUI_NAME, "swing");
        store.setDefault(USE_AVERAGE_SKILLS, true);
        store.setDefault(GENERATE_NAMES, true);
        store.setDefault(PRINT_ENTITY_CHANGE, false);
        store.setDefault(BOARD_WIDTH, 16);
        store.setDefault(BOARD_HEIGHT, 17);
        store.setDefault(MAP_WIDTH, 1);
        store.setDefault(MAP_HEIGHT, 1);
        store.setDefault(DEBUG_OUTPUT_ON,false);
        store.setDefault(MEMORY_DUMP_ON,false);
        setLocale(store.getString(LOCALE));
//        setMekHitLocLog();
	}
	
	/* (non-Javadoc)
	 * @see org.owl.common.preference.IClientPreference#getPrintEntityChange()
	 */
	public boolean getPrintEntityChange() {
		return false;
	}

	/* (non-Javadoc)
	 * @see org.owl.common.preference.IClientPreference#defaultAutoejectDisabled()
	 */
	public boolean defaultAutoejectDisabled() {
		return false;
	}

	/* (non-Javadoc)
	 * @see org.owl.common.preference.IClientPreference#useAverageSkills()
	 */
	public boolean useAverageSkills() {
		return false;
	}

	/* (non-Javadoc)
	 * @see org.owl.common.preference.IClientPreference#generateNames()
	 */
	public boolean generateNames() {
		return false;
	}

	/* (non-Javadoc)
	 * @see org.owl.common.preference.IClientPreference#getLastConnectAddr()
	 */
	public String getLastConnectAddr() {
		return null;
	}

	/* (non-Javadoc)
	 * @see org.owl.common.preference.IClientPreference#getLastConnectPort()
	 */
	public int getLastConnectPort() {
		return 0;
	}

	/* (non-Javadoc)
	 * @see org.owl.common.preference.IClientPreference#getLastPlayerName()
	 */
	public String getLastPlayerName() {
		return null;
	}

	/* (non-Javadoc)
	 * @see org.owl.common.preference.IClientPreference#getLastServerPass()
	 */
	public String getLastServerPass() {
		return null;
	}

	/* (non-Javadoc)
	 * @see org.owl.common.preference.IClientPreference#getLastServerPort()
	 */
	public int getLastServerPort() {
		return 0;
	}

	/* (non-Javadoc)
	 * @see org.owl.common.preference.IClientPreference#getLocale()
	 */
	public Locale getLocale() {
		return null;
	}

	/* (non-Javadoc)
	 * @see org.owl.common.preference.IClientPreference#getLocaleString()
	 */
	public String getLocaleString() {
		return null;
	}

	/* (non-Javadoc)
	 * @see org.owl.common.preference.IClientPreference#getMapTileset()
	 */
	public String getMapTileset() {
		return null;
	}

	/* (non-Javadoc)
	 * @see org.owl.common.preference.IClientPreference#getMaxPathfinderTime()
	 */
	public int getMaxPathfinderTime() {
		return 0;
	}

	/* (non-Javadoc)
	 * @see org.owl.common.preference.IClientPreference#getDataDirectory()
	 */
	public String getDataDirectory() {
		return null;
	}

	/* (non-Javadoc)
	 * @see org.owl.common.preference.IClientPreference#getLogDirectory()
	 */
	public String getLogDirectory() {
		return null;
	}

	/* (non-Javadoc)
	 * @see org.owl.common.preference.IClientPreference#getMechDirectory()
	 */
	public String getMechDirectory() {
		return null;
	}

	/* (non-Javadoc)
	 * @see org.owl.common.preference.IClientPreference#getMekHitLocLog()
	 */
	public PrintWriter getMekHitLocLog() {
		return null;
	}

	/* (non-Javadoc)
	 * @see org.owl.common.preference.IClientPreference#getMetaServerName()
	 */
	public String getMetaServerName() {
		return null;
	}

	/* (non-Javadoc)
	 * @see org.owl.common.preference.IClientPreference#setMetaServerName(java.lang.String)
	 */
	public void setMetaServerName(String name) {

	}

	/* (non-Javadoc)
	 * @see org.owl.common.preference.IClientPreference#getGoalPlayers()
	 */
	public int getGoalPlayers() {
		return 0;
	}

	/* (non-Javadoc)
	 * @see org.owl.common.preference.IClientPreference#setGoalPlayers(int)
	 */
	public void setGoalPlayers(int n) {

	}

	/* (non-Javadoc)
	 * @see org.owl.common.preference.IClientPreference#getGameLogFilename()
	 */
	public String getGameLogFilename() {
		return null;
	}

	/* (non-Javadoc)
	 * @see org.owl.common.preference.IClientPreference#stampFilenames()
	 */
	public boolean stampFilenames() {
		return false;
	}

	/* (non-Javadoc)
	 * @see org.owl.common.preference.IClientPreference#getStampFormat()
	 */
	public String getStampFormat() {
		return null;
	}

	/* (non-Javadoc)
	 * @see org.owl.common.preference.IClientPreference#getShowUnitId()
	 */
	public boolean getShowUnitId() {
		return false;
	}

	/* (non-Javadoc)
	 * @see org.owl.common.preference.IClientPreference#getUnitStartChar()
	 */
	public char getUnitStartChar() {
		return 0;
	}

	/* (non-Javadoc)
	 * @see org.owl.common.preference.IClientPreference#keepGameLog()
	 */
	public boolean keepGameLog() {
		return false;
	}

	/* (non-Javadoc)
	 * @see org.owl.common.preference.IClientPreference#memoryDumpOn()
	 */
	public boolean memoryDumpOn() {
		return false;
	}

	/* (non-Javadoc)
	 * @see org.owl.common.preference.IClientPreference#debugOutputOn()
	 */
	public boolean debugOutputOn() {
		return false;
	}

	/* (non-Javadoc)
	 * @see org.owl.common.preference.IClientPreference#setDefaultAutoejectDisabled(boolean)
	 */
	public void setDefaultAutoejectDisabled(boolean state) {

	}

	/* (non-Javadoc)
	 * @see org.owl.common.preference.IClientPreference#setUseAverageSkills(boolean)
	 */
	public void setUseAverageSkills(boolean state) {

	}

	/* (non-Javadoc)
	 * @see org.owl.common.preference.IClientPreference#setGenerateNames(boolean)
	 */
	public void setGenerateNames(boolean state) {

	}

	/* (non-Javadoc)
	 * @see org.owl.common.preference.IClientPreference#setKeepGameLog(boolean)
	 */
	public void setKeepGameLog(boolean state) {

	}

	/* (non-Javadoc)
	 * @see org.owl.common.preference.IClientPreference#setLastConnectAddr(java.lang.String)
	 */
	public void setLastConnectAddr(String serverAddr) {

	}

	/* (non-Javadoc)
	 * @see org.owl.common.preference.IClientPreference#setLastConnectPort(int)
	 */
	public void setLastConnectPort(int port) {
	}

	/* (non-Javadoc)
	 * @see org.owl.common.preference.IClientPreference#setLastPlayerCamoName(java.lang.String)
	 */
	public void setLastPlayerCamoName(String camoFileName) {

	}

	/* (non-Javadoc)
	 * @see org.owl.common.preference.IClientPreference#setLastPlayerCategory(java.lang.String)
	 */
	public void setLastPlayerCategory(String camoCategory) {

	}

	/* (non-Javadoc)
	 * @see org.owl.common.preference.IClientPreference#setLastPlayerColor(int)
	 */
	public void setLastPlayerColor(int colorIndex) {

	}

	/* (non-Javadoc)
	 * @see org.owl.common.preference.IClientPreference#setLastPlayerName(java.lang.String)
	 */
	public void setLastPlayerName(String name) {

	}

	/* (non-Javadoc)
	 * @see org.owl.common.preference.IClientPreference#setLastServerPass(java.lang.String)
	 */
	public void setLastServerPass(String serverPass) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.owl.common.preference.IClientPreference#setLastServerPort(int)
	 */
	public void setLastServerPort(int port) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.owl.common.preference.IClientPreference#setLocale(java.lang.String)
	 */
	public void setLocale(String text) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.owl.common.preference.IClientPreference#setMapTileset(java.lang.String)
	 */
	public void setMapTileset(String filename) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.owl.common.preference.IClientPreference#setMaxPathfinderTime(int)
	 */
	public void setMaxPathfinderTime(int i) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.owl.common.preference.IClientPreference#setGameLogFilename(java.lang.String)
	 */
	public void setGameLogFilename(String text) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.owl.common.preference.IClientPreference#setStampFilenames(boolean)
	 */
	public void setStampFilenames(boolean state) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.owl.common.preference.IClientPreference#setStampFormat(java.lang.String)
	 */
	public void setStampFormat(String text) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.owl.common.preference.IClientPreference#setShowUnitId(boolean)
	 */
	public void setShowUnitId(boolean state) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.owl.common.preference.IClientPreference#setUnitStartChar(char)
	 */
	public void setUnitStartChar(char c) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.owl.common.preference.IClientPreference#getGUIName()
	 */
	public String getGUIName() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.owl.common.preference.IClientPreference#setGUIName(java.lang.String)
	 */
	public void setGUIName(String guiName) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.owl.common.preference.IClientPreference#getBoardWidth()
	 */
	public int getBoardWidth() {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see org.owl.common.preference.IClientPreference#getBoardHeight()
	 */
	public int getBoardHeight() {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see org.owl.common.preference.IClientPreference#getMapWidth()
	 */
	public int getMapWidth() {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see org.owl.common.preference.IClientPreference#getMapHeight()
	 */
	public int getMapHeight() {
		// TODO Auto-generated method stub
		return 0;
	}

}
