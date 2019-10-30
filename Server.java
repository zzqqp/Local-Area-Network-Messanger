import java.io.*;
import java.net.*;
import java.sql.*;
import java.util.Vector;
class ServerThread extends Thread{
private Socket socket;
private BufferedReader in;
private PrintWriter out;
int no;
public ServerThread(Socket s) throws IOException {
   socket=s;
  in=new BufferedReader(new InputStreamReader(socket.getInputStream()));
  out=new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())),true);
   start();
   }

public void run(){
 try{ while(true){
                    String str=in.readLine();//
                    if(str.equals("end"))break;//
       else if(str.equals("login")) {
          try{ Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");//Connect Database
         Connection c=DriverManager.getConnection("jdbc:odbc:javalan"," "," ");
         String sql="select nickname,password from lan where lanid=?";                                                               
                                                                      
  PreparedStatement prepare=c.prepareCall(sql);
          String lanid=in.readLine();
                int g=Integer.parseInt(lanid);
                 System.out.println(lanid);
                 String passwd=in.readLine().trim();
                 System.out.println(passwd);
                 prepare.clearParameters();
                 prepare.setInt(1,g);
                 ResultSet r=prepare.executeQuery();
                 if(r.next()){
                     String pass=r.getString("password").trim();
                     System.out.println(pass);
                     if(passwd.regionMatches(0,pass,0,pass.length()))
{ out.println("ok");

                      //*************register ipaddress
                      String setip="update lan set ip=? where lanid=?";
                      PreparedStatement prest=c.prepareCall(setip);
                      prest.clearParameters();
                      prest.setString(1,socket.getInetAddress().getHostAddress());
                      prest.setInt(2,g);
                      int set=prest.executeUpdate();
                      System.out.println(set);
                      //*************ipaddress
                      //set status online
                      String status="update lan set status=1 where lanid=?";
                      PreparedStatement prest2=c.prepareCall(status);
                       prest2.clearParameters();
                       prest2.setInt(1,g);
                       int set2=prest2.executeUpdate();
                      System.out.println(set2);
                      //set online
}

                      else out.println("false");r.close();c.close();}
                 else{ out.println("false");
                 System.out.println("false");
                r.close();
                c.close();}
                }catch (Exception e){e.printStackTrace();}
                socket.close();
                }//end login
 
else  if(str.equals("new")){
   try{ Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
        Connection c2=DriverManager.getConnection("jdbc:odbc:javalan"," "," ");
String newsql="insert into lan(nickname,password,email,info,place,pic) values(?,?,?,?,?,?)";

       PreparedStatement prepare2=c2.prepareCall(newsql);
       String nickname=in.readLine().trim();
       String password=in.readLine().trim();
       String email=in.readLine().trim();
       String info=in.readLine().trim();
       String place=in.readLine().trim();
       int picindex=Integer.parseInt(in.readLine());
       prepare2.clearParameters();
       prepare2.setString(1,nickname);
       prepare2.setString(2,password);
       prepare2.setString(3,email);
       prepare2.setString(4,info);
       prepare2.setString(5,place);
       prepare2.setInt(6,picindex);
       int r3=prepare2.executeUpdate();
String sql2="select lanid from lan where nickname=?";

       PreparedStatement prepare3=c2.prepareCall(sql2);
        prepare3.clearParameters();
       prepare3.setString(1,nickname);
       ResultSet r2=prepare3.executeQuery();
     while(r2.next()){
      //out.println(r2.getInt(1));
      no=r2.getInt(1);
      System.out.println(no);
     }
      out.println(no);
      out.println("ok");
c2.close();

     }catch (Exception e){e.printStackTrace();out.println("false");}
     socket.close();
   }//end new

else if(str.equals("find")){
try{ Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
        Connection c3=DriverManager.getConnection("jdbc:odbc:javalan"," "," ");

     String find="select nickname,sex,place,ip,email,info from lan";
    Statement st=c3.createStatement();
    ResultSet result=st.executeQuery(find);
     while(result.next()){
     out.println(result.getString("nickname"));
     out.println(result.getString("sex"));
     out.println(result.getString("place"));
     out.println(result.getString("ip"));
      out.println(result.getString("email"));
       out.println(result.getString("info"));
     }//while end
     out.println("over");

     int d,x;
boolean y;
     ResultSet iset=st.executeQuery("select lanid,pic,status from lan");
     while(iset.next()){
     d=iset.getInt("lanid");
     out.println(d);
     x=iset.getInt("pic");//pic info
     out.println(x);
     y=iset.getBoolean("status");
      if (y){out.println("1");}
           else {out.println("0");}
      //System.out.println(d);
     }
    // end send jlanid
     iset.close();
     /////////lanid end
      c3.close();result.close();
}catch (Exception e){e.printStackTrace();System.out.println("false");}
//socket.close();
}//end find

else if(str.equals("friend")){
try{ Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
        Connection c4=DriverManager.getConnection("jdbc:odbc:javalan"," "," ");

     String friend="select friend from friend where lanid=?";
     PreparedStatement prepare4=c4.prepareCall(friend);
        prepare4.clearParameters();
         int lanid=Integer.parseInt(in.readLine());
         System.out.println(lanid);
         prepare4.setInt(1,lanid);
       ResultSet r4=prepare4.executeQuery();
       Vector friendno=new Vector();
        while(r4.next()){
      friendno.add(new Integer(r4.getInt(1)));
     }
//read friend info

    out.println(friendno.size());
          for(int i=0;i<friendno.size();i++){
         String friendinfo="select nickname,lanid,ip,status,pic,email,info from lan where lanid=?";
      PreparedStatement prepare5=c4.prepareCall(friendinfo);
      prepare5.clearParameters();
       prepare5.setObject(1,friendno.get(i));
      ResultSet r5=prepare5.executeQuery();
      boolean status;
         while(r5.next()){
       out.println(r5.getString("nickname"));
           out.println(r5.getInt("lanid"));
           out.println(r5.getString("ip"));
         status=r5.getBoolean("status");
         if (status)out.println("1");
           else {out.println("0");}
        out.println(r5.getInt("pic"));
        out.println(r5.getString("email"));
        out.println(r5.getString("info"));
     } //while
     r5.close();
}

     out.println("over");
     System.out.println("over");
      c4.close();r4.close();
}catch (Exception e){e.printStackTrace();System.out.println("false");}
//socket.close();
}//end friend

 else if(str.equals("addfriend")){
System.out.println("add");
 try{ Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
        Connection c6=DriverManager.getConnection("jdbc:odbc:javalan"," "," ");

        int friendlanid=Integer.parseInt(in.readLine());
          System.out.println(friendlanid);
         int mylanid=Integer.parseInt(in.readLine());
            System.out.println(mylanid);
            String addfriend="insert into friend values(?,?)";
             PreparedStatement prepare6=c6.prepareCall(addfriend);
      prepare6.clearParameters();
       prepare6.setInt(1,mylanid);
       prepare6.setInt(2,friendlanid);
       int  r6=0;
      r6=prepare6.executeUpdate();
      if(r6==1) System.out.println("ok  addfrien");
      else  System.out.println("false addfriend");

}catch (Exception e){e.printStackTrace();System.out.println("false");}

//socket.close();
System.out.println("over addfriend");
}//end addfriend

//add new friend who add me

 else if(str.equals("addnewfriend")){
System.out.println("add");
 try{ Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
        Connection c6=DriverManager.getConnection("jdbc:odbc:javalan"," "," ");

        int friendlanid=Integer.parseInt(in.readLine());
          System.out.println(friendlanid);
         int mylanid=Integer.parseInt(in.readLine());
            System.out.println(mylanid);
            String addfriend="insert into friend values(?,?)";
             PreparedStatement prepare6=c6.prepareCall(addfriend);
      prepare6.clearParameters();
       prepare6.setInt(1,mylanid);
       prepare6.setInt(2,friendlanid);
       int  r6=0;
      r6=prepare6.executeUpdate();
      if(r6==1) System.out.println("ok  addfrien");
      else  System.out.println("false addfriend");

String friendinfo="select nickname,lanid,ip,status,pic,email,info from lan where lanid=?";

      PreparedStatement prepare5=c6.prepareCall(friendinfo);
      prepare5.clearParameters();
       prepare5.setInt(1,friendlanid);
      ResultSet r5=prepare5.executeQuery();
      boolean status;
         while(r5.next()){
         System.out.println("dsf");
       out.println(r5.getString("nickname"));
           out.println(r5.getInt("lanid"));
           out.println(r5.getString("ip"));
         status=r5.getBoolean("status");
         if (status)out.println("1");
           else {out.println("0");}
        out.println(r5.getInt("pic"));
        out.println(r5.getString("email"));
        out.println(r5.getString("info"));
     } //while
       out.println("over");
     r5.close();
     c6.close();
}catch (Exception e){e.printStackTrace();System.out.println("false");}
System.out.println("over addnewfriend");
}//end addfriend

//delete friend

else if(str.equals("delfriend")){
System.out.println("del");
try{ Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
        Connection c7=DriverManager.getConnection("jdbc:odbc:javalan"," "," ");

        int friendlanid=Integer.parseInt(in.readLine());
          System.out.println(friendlanid);
         int mylanid=Integer.parseInt(in.readLine());
            System.out.println(mylanid);
            String addfriend="delete from friend where lanid=? and friend=?";
             PreparedStatement prepare7=c7.prepareCall(addfriend);
      prepare7.clearParameters();
       prepare7.setInt(1,mylanid);
       prepare7.setInt(2,friendlanid);
       int  r7=0;
      r7=prepare7.executeUpdate();
      if(r7==1) System.out.println("ok  delfrien");
      else  System.out.println("false delfriend");
}catch (Exception e){e.printStackTrace();System.out.println("del false");}
}//end delete friend

else if(str.equals("logout")){
try{ Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
        Connection c8=DriverManager.getConnection("jdbc:odbc:javalan"," "," ");

         int mylanid=Integer.parseInt(in.readLine());
            System.out.println(mylanid);
             String status="update lan set status=0 , ip=' ' where lanid=?";
                      PreparedStatement prest8=c8.prepareCall(status);
                       prest8.clearParameters();
                       prest8.setInt(1,mylanid);
                   int r8=prest8.executeUpdate();
                     if(r8==1) System.out.println("ok  logout");
      else  System.out.println("false logout");
}catch (Exception e){e.printStackTrace();System.out.println("logout false");}
}//logout end

//get who add me as friend

else if(str.equals("getwhoaddme")){
System.out.println("getwhoaddme");
 try{ Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
        Connection c9=DriverManager.getConnection("jdbc:odbc:javalan"," "," ");

        int mylanid=Integer.parseInt(in.readLine());
            System.out.println(mylanid);
            String getwhoaddme="select lanid from friend where friend=?";
             PreparedStatement prepare6=c9.prepareCall(getwhoaddme);
      prepare6.clearParameters();
       prepare6.setInt(1,mylanid);
      ResultSet r6=prepare6.executeQuery();
    Vector who=new Vector();
        while(r6.next()){
      who.add(new Integer(r6.getInt(1)));
   }//end while

    for(int i=0;i<who.size();i++){
     String whoinfo="select ip from lan where lanid=? and status=1";
      PreparedStatement prepare=c9.prepareCall(whoinfo);
      prepare.clearParameters();
       prepare.setObject(1,who.get(i));
      ResultSet r=prepare.executeQuery();
       while(r.next()){
         out.println(r.getString("ip"));
            } //while
           r.close();
         }//for
     out.println("over");
     System.out.println("over");
      c9.close();r6.close();
}catch (Exception e){e.printStackTrace();System.out.println("false");}
}//end get who add me as friend

   System.out.println("Echo ing :"+str);
    }  System.out.println("Close...");
      }catch(IOException e){}
      finally {try{socket.close();}
                     catch(IOException e){}
    }
  }
}
public class Server{
public static void main(String args[])throws IOException{
ServerSocket s=new ServerSocket(8080);
System.out.println("Server start.."+s);
try{
     while(true){Socket socket=s.accept();
                      System.out.println("Connectino accept:"+socket);
                  try{new ServerThread(socket);
                  }catch(IOException e){socket.close();}
                }
      }finally{s.close();}
    }
}
