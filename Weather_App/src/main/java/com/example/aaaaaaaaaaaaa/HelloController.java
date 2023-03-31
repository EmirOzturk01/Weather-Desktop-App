package com.example.aaaaaaaaaaaaa;

import javafx.animation.RotateTransition;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.awt.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.*;


public class HelloController implements Initializable  {
    @FXML
    private ImageView clearSky,scatteredClouds,fewClouds,brokenClouds,showerRain,rain,thunderstorm,snow,mist;
    @FXML
    private ImageView slideImage;
    @FXML
    private TextField search;
    @FXML
    private Label weatherText;
    @FXML
    private Label hum,tempMin,tempMax,felt;
    @FXML
    private Label cityCountry;
    @FXML
    private AnchorPane noConnection;
    @FXML
    private AnchorPane navPane;
    @FXML
    private AnchorPane truePane;
    @FXML
    private AnchorPane notFound;
    @FXML
    private ImageView minimize;
    @FXML
    private ImageView menuIcon;
    @FXML
    private AnchorPane menuBar;
    @FXML
    public AnchorPane clipboard;


    public void setVisible(City city,String description){
        imageVisible();
        if (description.equals("clear sky"))
            clearSky.setVisible(true);

        else if(city.getMain().equals("Clouds")){
            if (description.equals("few clouds"))
                fewClouds.setVisible(true);
            else if (description.equals("scattered clouds"))
                scatteredClouds.setVisible(true);
            else
                brokenClouds.setVisible(true);
        }

        else if(city.getMain().equals("Rain") || city.getMain().equals("Drizzle")){
            if (description.equals("shower rain"))
                showerRain.setVisible(true);
            else
                rain.setVisible(true);
        }

        else if (city.getMain().equals("Thunderstorm"))
            thunderstorm.setVisible(true);

        else if (city.getMain().equals("Snow") || city.getMain().equals("snow"))
            snow.setVisible(true);

        else if (description.equals("mist"))
            mist.setVisible(true);
    }
    public void imageVisible(){
        clearSky.setVisible(false);
        scatteredClouds.setVisible(false);
        fewClouds.setVisible(false);
        brokenClouds.setVisible(false);
        showerRain.setVisible(false);
        rain.setVisible(false);
        thunderstorm.setVisible(false);
        snow.setVisible(false);
        mist.setVisible(false);
    }
    int helper=0;
    public void SlideDown(){
        if(helper==0){
            TranslateTransition translate = new TranslateTransition();
           // translate.setByY(80);
            translate.setToY(80);
            translate.setDuration(Duration.millis(1000));
            translate.setCycleCount(0);
            translate.setAutoReverse(false);
            translate.setNode(navPane);
            translate.play();
            helper=1;
            slideImage.setRotate(180);
        }
        else{
            TranslateTransition translate = new TranslateTransition();
            translate.setToY(0);
            translate.setDuration(Duration.millis(1000));
            translate.setCycleCount(0);
            translate.setAutoReverse(false);
            translate.setNode(navPane);
            translate.play();
            helper=0;
            slideImage.setRotate(0);
        }

    }
    int menuBarIcon=0;
    public void menuIcon(){
        System.out.println("javafx.runtime.version: " + System.getProperty("javafx.runtime.version"));
        if(menuBarIcon==0){
            RotateTransition transition = new RotateTransition(Duration.millis(300),menuIcon);
            transition.setToAngle(180);
            transition.play();
            menuBar.setVisible(true);
            menuBarIcon=1;
        }
        else{
            RotateTransition transition = new RotateTransition(Duration.millis(300),menuIcon);
            transition.setToAngle(0);
            transition.play();
            menuBar.setVisible(false);
            menuBarIcon=0;
        }

    }
    int count=0;
    public void weatherSnapshot(){

        if(truePane.isVisible()){
            Clipboard clipboard = Clipboard.getSystemClipboard();
            ClipboardContent content = new ClipboardContent();
            WritableImage image = truePane.snapshot(new SnapshotParameters(), null);
            content.putImage(image);
            clipboard.setContent(content);


            copiedToClipboardUp();
            Timer myTimer=new Timer();
            TimerTask task =new TimerTask() {
                @Override
                public void run() {
                    if(count==1){
                        invisibleClip();

                        myTimer.cancel();
                    }
                    else
                    {
                        copiedToClipboardDown();
                        count++;
                    }
                }
            };
            myTimer.schedule(task,1800,500);
            count=0;
        }
    }
    public void invisibleClip(){
        clipboard.setVisible(false);
    }
    public void minimize(){
        Stage stage = (Stage) minimize.getScene().getWindow();
        stage.setIconified(true);
    }
    public void exit(){
        System.exit(0);
    }
    public void city() throws IOException {
        try {
            MyApiKey myApiKey = new MyApiKey();
        String city = search.getText().toString();
        String s = "https://api.openweathermap.org/data/2.5/weather?q="+city+"&appid="+myApiKey.getApiKey();

        URL url = new URL(s);
            HttpURLConnection conn =(HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();
            int reponseCode = conn.getResponseCode();

            if(reponseCode !=200){
                throw new RuntimeException("HttpResponceCode: "+reponseCode);

            }else {
                StringBuilder informationString = new StringBuilder();
                Scanner scanner = new Scanner(url.openStream());
                while (scanner.hasNext()){
                    informationString.append(scanner.nextLine());
                }
                scanner.close();


                String data = informationString.toString();



                JSONParser parser = new JSONParser();
                Object obj = parser.parse(data);
                JSONObject jsonObject = (JSONObject) obj;
                for(Iterator iterator = jsonObject.keySet().iterator(); iterator.hasNext();) {
                    String key = (String) iterator.next();
                    System.out.println(key);


                }



                //sys parse
                String sysStr = String.valueOf(jsonObject.get("sys"));
                Object sys = parser.parse(sysStr);
                JSONObject sysObject = (JSONObject) sys;
                for (Iterator iterator= sysObject.keySet().iterator(); iterator.hasNext();){
                    String key = (String) iterator.next();
                }
                //



                //weather parse
                String weather = jsonObject.get("weather").toString();
                weather=weather.substring(1);
                weather=weather.substring(0,weather.length()-1);


                String weatherStr = String.valueOf(weather);
                Object weatherObj = parser.parse(weatherStr);
                JSONObject weatherObject = (JSONObject) weatherObj;
                for (Iterator iterator = weatherObject.keySet().iterator(); iterator.hasNext();){
                    String key = (String) iterator.next();

                }
                //


                //main parse
                String mainStr = String.valueOf(jsonObject.get("main"));
                Object main = parser.parse(mainStr);
                JSONObject mainObject =(JSONObject) main;
                for(Iterator iterator = mainObject.keySet().iterator(); iterator.hasNext();){

                    String key = (String) iterator.next();

                }
                //


                City city1 = new City((String) jsonObject.get("name") ,(Double)mainObject.get("temp"),(Double) mainObject.get("temp_min"),
                        (Double) mainObject.get("feels_like"),(Double) mainObject.get("temp_max"),(String) sysObject.get("country"),
                        (String) weatherObject.get("description"),(String) weatherObject.get("main"));


                weatherText.setText(city1.getTemp()+"°C");
                cityCountry.setText(city1.getName()+"/"+city1.getCountry());
                hum.setText(mainObject.get("humidity").toString()+"%"+'\n'+"Humidity");
                tempMin.setText(city1.getTempMin().toString()+"°C"+'\n'+"Min");
                tempMax.setText(city1.getTempMax().toString()+"°C"+'\n'+"Max");
                felt.setText("Felt: " +city1.getFeelsLike().toString()+"°C");



                setVisible(city1,city1.getDescription());
                truePane.setVisible(true);
                notFound.setVisible(false);
                noConnection.setVisible(false);
                navPane.setVisible(true);


                System.gc();

            }


/*
            String imageUrl = "https://images.unsplash.com/photo-1513635269975-59663e0ac1ad?crop=entropy\\u0026cs=tinysrgb\\u0026fm=jpg\\u0026ixid=Mnw0MTIyOTZ8MHwxfHNlYXJjaHwxfHxsb25kb258ZW58MHx8fHwxNjc2NzcxMTcz\\u0026ixlib=rb-4.0.3\\u0026q=80";
            String destinationFile = "image.jpg";
            URL urlaa = new URL(imageUrl);
            InputStream is = urlaa.openStream();
            OutputStream os = new FileOutputStream(destinationFile);

            byte[] b = new byte[2048];
            int length;

            while ((length = is.read(b)) != -1) {
                os.write(b, 0, length);
            }

            is.close();
            os.close();
*/

        }catch(IOException e) {
            System.out.println("Internet baglantinizi kontrol ediniz.");
            truePane.setVisible(false);
            notFound.setVisible(false);
            noConnection.setVisible(true);
            navPane.setVisible(false);
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("Aradığınız şehir veya ülke bulunamadı");
            truePane.setVisible(false);
            notFound.setVisible(true);
            noConnection.setVisible(false);
            navPane.setVisible(false);
        }



    }
    public void copiedToClipboardUp(){
            clipboard.setVisible(true);
            TranslateTransition translate = new TranslateTransition();
            translate.setToY(-100);
            translate.setDuration(Duration.millis(1000));
            translate.setCycleCount(0);
            translate.setAutoReverse(false);
            translate.setNode(clipboard);
            translate.play();
    }
    public void copiedToClipboardDown(){
        TranslateTransition translate2 = new TranslateTransition();
        translate2.setToY(100);
        translate2.setDuration(Duration.millis(1000));
        translate2.setCycleCount(0);
        translate2.setAutoReverse(false);
        translate2.setNode(clipboard);
        translate2.play();

        translate2=null;
    }



    public void FavouriteAdd() throws IOException {
        int index= cityCountry.getText().indexOf('/');
        String city = cityCountry.getText().substring(0,index);

        //same text control
        BufferedReader reader = new BufferedReader(new FileReader("src/favourites.txt"));
        String line = reader.readLine();
        int a=0;
        while(line!=null){
            if(line.equals(city)){
                a++;
            }
            line=reader.readLine();
        }
        reader.close();
        if(a==0)
            favouriteTxtWriter();


    }
    public void favouriteTxtWriter() throws IOException {

        int index= cityCountry.getText().indexOf('/');
        String city = cityCountry.getText().substring(0,index);
        System.out.println(city+":<<<city");

        Writer output;
        output = new BufferedWriter(new FileWriter("src/favourites.txt", true));
        output.append(city+'\n');
        output.close();


    }

    public void googleMaps() throws URISyntaxException, IOException {
        Desktop desktop = Desktop.getDesktop();
        int index= cityCountry.getText().indexOf('/');
        String city = cityCountry.getText().substring(0,index);
        city=city.replaceAll("\\s", "+");
        String link = "https://www.google.com/maps/place/"+city;
        desktop.browse(new URI(link));
        desktop=null;
    }
    public void googleImages() throws URISyntaxException, IOException {
        Desktop desktop = Desktop.getDesktop();
        int index= cityCountry.getText().indexOf('/');
        String city = cityCountry.getText().substring(0,index);
        city=city.replaceAll("\\s", "");
        String link = "https://www.google.com/search?tbm=isch&q="+city;
        desktop.browse(new URI(link));
        desktop=null;
    }



    SceneContext sceneContext = new SceneContext();

    public void switchToFav() throws IOException, InterruptedException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("favourite.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        sceneContext.setOldScene(clipboard.getScene());
        sceneContext.switchScene(scene);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        truePane.setVisible(false);
        notFound.setVisible(false);
        noConnection.setVisible(false);
        navPane.setVisible(false);
        menuBar.setVisible(false);
        clipboard.setVisible(false);
        FavCardController.txtLineCounter=0;
        System.gc();
    }

}