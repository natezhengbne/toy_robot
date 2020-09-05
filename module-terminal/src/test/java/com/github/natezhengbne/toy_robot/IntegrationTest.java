package com.github.natezhengbne.toy_robot;

import com.github.natezhengbne.toy_robot.service.InputService;
import com.github.natezhengbne.toy_robot.service.TableService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.*;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Slf4j
@ActiveProfiles("inputTest")
@ExtendWith(SpringExtension.class)
@SpringBootTest
class IntegrationTest {

    @Autowired
    private TableService tableService;
    @Autowired
    private InputService inputService;

    private String externalFolder;

    @BeforeEach
    void setUp() {
        tableService.reset();
        externalFolder = System.getProperty("test.folder");
    }

    @DisplayName("Read file from external")
    @Test
    void fromExternal() throws FileNotFoundException {
        if(externalFolder==null || externalFolder.length()==0){
            return;
        }
        File folder = new File(externalFolder);
        assertTrue(folder.isDirectory());

        File[] files = folder.listFiles();
        if(files==null){
            return;
        }
        for(File file : files){
            if(!file.isFile()){
                continue;
            }
            this.test(file);
        }
    }

    @DisplayName("Read file from resources folder")
    @Test
    void fromResources() throws IOException {
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        Resource[] resources = resolver.getResources("robot_test/*.*");

        int i=1;
        log.info(" Total test files: "+ resources.length);
        for (Resource resource : resources) {
            this.test(resource.getFile());
        }
    }

    private void test(File file) throws FileNotFoundException {
        Scanner scanner = new Scanner(file);
        String filename = file.getName();
        log.info(" -> File Name: "+filename);

        String lastLine = null;
        while (scanner.hasNext()){
            String line = scanner.nextLine();
            log.info(" -> "+line);
            if(line.toUpperCase().startsWith("REPORT")){
                lastLine = inputService.handle(line);
            }else{
                if(line.startsWith("Output:")){
                    assertEquals(lastLine, line);
                }else{
                    inputService.handle(line);
                }
            }


        }
        scanner.close();

    }

}
