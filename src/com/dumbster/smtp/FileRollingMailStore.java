/*
 * Copyright 2012 athalay.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.dumbster.smtp;

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;

/**
 *
 * @author athalay
 */
public class FileRollingMailStore extends RollingMailStore {
    
    private String directory;

    public FileRollingMailStore() {
        this.directory = System.getProperty("user.dir");
        appendSeparator();
    }

    public FileRollingMailStore(String directory) {
        this.directory = directory;
        appendSeparator();
    }
    
    private void appendSeparator(){
        if(!directory.endsWith(File.separator)){
            directory = directory + File.separator;
        }
    }

    @Override
    public void addMessage(MailMessage message) {
        super.addMessage(message);
        saveMessageToFile(message);
    }
    
    private void saveMessageToFile(MailMessage message){
        StringBuilder sb = new StringBuilder();
        Iterator<String> headerNameIterator = message.getHeaderNames();
        while(headerNameIterator.hasNext()){
            String headerName = headerNameIterator.next();
            String[] headerBodys = message.getHeaderValues(headerName);
            for (String headerBody : headerBodys) {
                sb.append(headerName);
                sb.append(": ");
                sb.append(headerBody);
                sb.append("\n");
            }
        }
        sb.append("\n");
        sb.append(message.getBody());
        try{
            createFile(sb.toString());
        } catch(Exception e){
            e.printStackTrace();
        }
    }
    
    private void createFile(String email) throws FileNotFoundException, UnsupportedEncodingException, IOException{
        DateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss-SSS");
        String fileName = dateFormat.format(new Date()) + ".eml";
        String fullFileName = directory + fileName;
        System.out.println("Saving file : " + fullFileName);
        Writer out = new OutputStreamWriter(new FileOutputStream(fullFileName), "ASCII");
        out.write(email);
        out.close();
    }
}
