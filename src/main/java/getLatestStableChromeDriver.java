import com.profesorfalken.jpowershell.PowerShell;
import com.profesorfalken.jpowershell.PowerShellNotAvailableException;
import com.profesorfalken.jpowershell.PowerShellResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class getLatestStableChromeDriver {

    public static void executePowerShell(){

        try (PowerShell powerShell = PowerShell.openSession()) {
            //Increase timeout to give enough time to the script to finish
            Map<String, String> config = new HashMap<String, String>();
            config.put("maxWait", "80000");

            //Execute script
            PowerShellResponse response = powerShell.configuration(config).executeScript("src/main/poweshell/getChromedriver.ps1");

            //Print results if the script
            System.out.println("Script output:" + response.getCommandOutput());
        } catch(PowerShellNotAvailableException ex) {
            //Handle error when PowerShell is not available in the system
            //Maybe try in another way?
        }

    }

    public static void main(String[] args) throws IOException, InterruptedException {
        executePowerShell();
    }
}
