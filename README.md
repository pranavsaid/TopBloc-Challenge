# TopBloc-Challenge

This application uses Apache POI library to parse data from Excel sheet. Upon parsing this application builds JSON using the parsed data and sends as a HTTP request to the server.

IDE used - Netbeans 
Maven Dependencies used are

POI:     
 
	 <dependency>
            <groupId>org.apache.poi</groupId>
            <artifactId>poi-ooxml</artifactId>
            <version>3.15</version>
         </dependency>

JSON-simple:  
    
	<dependency>
            <groupId>com.googlecode.json-simple</groupId>
            <artifactId>json-simple</artifactId>
            <version>1.1</version>
        </dependency>

httpclinet:      
	<dependency>
            <groupId>org.apache.httpcomponents</groupId>
            <artifactId>httpclient</artifactId>
            <version>4.5.5</version>
	</dependency>
