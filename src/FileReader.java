import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;

public class FileReader {

	private Path newPath;
	
	public FileReader(String path)
    {
		newPath = Paths.get(path);
		
		if (!Files.exists(newPath, new LinkOption[]{ LinkOption.NOFOLLOW_LINKS}))
		{
			try {
				Files.createFile(newPath);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public String loadFile()
	{
		byte[] bytes = null;
		
		try {
			bytes = Files.readAllBytes(newPath);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		byte[] newBytes = Base64.getDecoder().decode(bytes);
						
		String output = new String(newBytes);
		
		return output;
	}
	
	public void writeFile(String input)
	{
	    String encoded = Base64.getEncoder().encodeToString(input.getBytes());
	    
		try
        {
            Files.write(newPath, encoded.getBytes());
        } catch (IOException e)
        {
            e.printStackTrace();
        }
	}
}
