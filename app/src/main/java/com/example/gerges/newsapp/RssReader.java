package com.example.gerges.newsapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 * Created by gerge on 24/02/2017.
 */

public class RssReader  extends AsyncTask<Void,Void,Void>{
    ArrayList<FeedItem> feedItems;

    RecyclerView recView;
    Context context;
    ProgressDialog progressDialog;
    URL url;
    String address = "http://www.vetogate.com/rss.aspx";
    private RecyclerView.LayoutManager layoutManager;


    public RssReader(Context  context, RecyclerView recView){
        this.recView = recView;

        this.context= context;
        progressDialog= new ProgressDialog(context);
        progressDialog.setMessage("Loading...");
    }
    @Override
    protected void onPreExecute() {
         progressDialog.show();
        super.onPreExecute();

    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);



        layoutManager = new LinearLayoutManager(context);

         recView.setLayoutManager(layoutManager);
         recView.addItemDecoration(new VerticalSpace(50));
        Adapter myAdapter = new Adapter(context, feedItems);
        recView.setAdapter(myAdapter);

        progressDialog.dismiss();

    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected Void doInBackground(Void... params) {
       processXml(getData());

        return null;

    }

    private void processXml(Document data){

        if(data != null){

           feedItems = new ArrayList<>();

            Element root = data.getDocumentElement();

            Node channel = root.getChildNodes().item(0);

  /// caused null pointer exception becuase item(1);
            NodeList items = channel.getChildNodes();


            for(int i = 0; i<items.getLength(); i++){

                Node currentChild = items.item(i);
                if(currentChild.getNodeName().equalsIgnoreCase("item")){

                    FeedItem item = new FeedItem();

                    NodeList  itemChilds = currentChild.getChildNodes();

                    for(int j=0 ; j<itemChilds.getLength(); j++ ){
                        Node current = itemChilds.item(j);
                        if(current.getNodeName().equalsIgnoreCase("title")){
                            item.setTitle(current.getTextContent());
                        }else if(current.getNodeName().equalsIgnoreCase("link")){
                            item.setLink(current.getTextContent());
                        }else if(current.getNodeName().equalsIgnoreCase("description")){
                            item.setDescription(current.getTextContent());
                        }else if(current.getNodeName().equalsIgnoreCase("pubDate")){
                            item.setDate(current.getTextContent());
                        }else if(current.getNodeName().equalsIgnoreCase("description")){
                            item.setDescription(current.getTextContent());
                        }else if(current.getNodeName().equalsIgnoreCase("enclosure")){
                            String url = current.getAttributes().item(0).getTextContent();

                            item.setThumbUrl(url);
                        }

                    }

                    feedItems.add(item);


                }
            }
        }
    }
   public Document getData(){
       try {
           url = new URL(address);

           HttpURLConnection  connection = (HttpURLConnection) url.openConnection();
           connection.setRequestMethod("GET");
           InputStream inputStream = connection.getInputStream();

           DocumentBuilderFactory  builderFactory = DocumentBuilderFactory.newInstance();
           DocumentBuilder builder = builderFactory.newDocumentBuilder();
           Document xmlDoc = builder.parse(inputStream);
           return xmlDoc;


       } catch (Exception e) {
           e.printStackTrace();
           return null;
       }


   }



}
