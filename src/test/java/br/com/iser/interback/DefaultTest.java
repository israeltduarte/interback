package br.com.iser.interback;

import br.com.iser.interback.config.DefaultTestConfig;
import com.google.common.base.Charsets;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.ContextConfiguration;

@AutoConfigureMockMvc
@SpringBootTest
@ContextConfiguration(classes = {DefaultTestConfig.class})
public abstract class DefaultTest {

  public static String readJsonFile(final String filePath) throws IOException {

    try {

      final File input = new ClassPathResource(filePath).getFile();
      return new String(Files.readAllBytes(Paths.get(input.getPath())), Charsets.UTF_8);
    } catch (IOException e) {

      throw e;
    }
  }
}
