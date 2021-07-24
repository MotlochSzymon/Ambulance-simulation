package ambulancesimulation.inputAndOutputHandling;

import java.io.File;

public interface InputFilesInterface {

    public void loadMapElementsFile(File mapFile) throws Exception;

    public void loadPatientsFile(File patientsFile) throws Exception;

}
