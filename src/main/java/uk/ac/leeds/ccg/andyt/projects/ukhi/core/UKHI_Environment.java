package uk.ac.leeds.ccg.andyt.projects.ukhi.core;

import java.io.File;
import uk.ac.leeds.ccg.andyt.generic.core.Generic_Environment;
import uk.ac.leeds.ccg.andyt.generic.data.waas.core.WaAS_Environment;
import uk.ac.leeds.ccg.andyt.generic.data.waas.data.WaAS_Data;
import uk.ac.leeds.ccg.andyt.projects.ukhi.io.UKHI_Files;

/**
 *
 * @author geoagdt
 */
public class UKHI_Environment extends UKHI_OutOfMemoryErrorHandler  {

    public transient UKHI_Strings strings;
    public transient UKHI_Files files;
    public transient Generic_Environment ge;
    public transient WaAS_Environment we;
    
    /**
     * This is the active log ID.
     */
    public int logID;
    
    /**
     * This is the main log ID.
     */
    public transient final int logIDMain;

    /**
     * This is the sub log ID.
     */
    public int logIDSub;
    
    public transient static final String EOL = System.getProperty("line.separator");

    /**
     * 
     * @param ge
     * @param wasDataDir 
     */
    public UKHI_Environment(Generic_Environment ge, File wasDataDir) {
        //Memory_Threshold = 3000000000L;
        strings = new UKHI_Strings();
        files = new UKHI_Files(strings);
        this.ge = ge;
        we = new WaAS_Environment(wasDataDir);
        File f = we.files.getEnvDataFile();
        if (f.exists()) {
            we.data = (WaAS_Data) ge.io.readObject(f);
            we.data.env = we;
        } else {
            we.data = new WaAS_Data(we);
        }
        logID = ge.initLog(UKHI_Strings.s_UKHI);
        logIDMain = logID;
    }

    /**
     * @return See {@link WaAS_Environment#checkAndMaybeFreeMemory()}.
     */
    @Override
    public boolean checkAndMaybeFreeMemory() {
        return we.checkAndMaybeFreeMemory();
    }

    /**
     * @param hoome IFF true then {@link java.lang.OutOfMemoryError} is handled.
     * @return See {@link WaAS_Environment#swapDataAny()}.
     */
    @Override
    public boolean swapDataAny(boolean hoome) {
        return we.swapDataAny(hoome);
    }

    /**
     * @return See {@link WaAS_Environment#swapDataAny()}.
     */
    @Override
    public boolean swapDataAny() {
        return we.swapDataAny();
    }

    /**
     * @return See {@link WaAS_Environment#cacheData()}.
     */
    public boolean clearSomeData() {
        return we.data.clearSomeData();
    }

    /**
     * @return See {@link WaAS_Environment#clearAllData()}.
     */
    public int clearAllData() {
        return we.clearAllData();
    }
    
    /**
     * See {@link WaAS_Environment#cacheData()}.
     */
    public void cacheData() {
        we.cacheData();
    }

    /**
     * For convenience. See
     * {@link Generic_Environment#logStartTag(java.lang.String, int)}.
     *
     * @param s The tag name.
     */
    public final void logStartTag(String s) {
        ge.logStartTag(s, logID);
    }
    
    /**
     * For convenience. See
     * {@link Generic_Environment#log(java.lang.String, int)}.
     * @param s The message to be logged.
     */
    public void log(String s) {
        ge.log(s, logID);
    }
    
    /**
     * For convenience. See
     * {@link Generic_Environment#logEndTag(java.lang.String, int)}.
     *
     * @param s The tag name.
     */
    public final void logEndTag(String s) {
        ge.logEndTag(s, logID);
    }
}