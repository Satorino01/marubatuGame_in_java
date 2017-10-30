//二重配列で作った◯×ゲームです。打たれてない場所は「■」で表されます。「■」は配列内では０、「○」なら１、「×」なら１００が入ります。
//配列の座標は３目並べの場合、入力時は横縦共に１〜３で入力しますが、内部では０〜２の二重配列構造となっております。
//３目並べの場合、勝ちの判定は各列、各行、斜めの各合計値が３なら◯の勝ち。各合計値が３００なら×の勝ちです。
//各行、各列、各斜めのいずれかの合計値＝＝マス目＊１がtrueなら◯の勝ち。各合計値＝＝マス目＊１００がtrueなら×の勝ちという仕組みです。
//故に１００目並べ以上だと勝利判定がおかしくなってしまいます。
import java.io.*;
public class marubatugameArray {
    static int Masu_number = 100;
    static int Masu[][]= new int [Masu_number][Masu_number];
    public static void main(String[] args) {
        System.out.print("何目並べをやりますか？１〜９９までの値で入力してください：");
        Masu_number = inputInt();
        System.out.println("「"+Masu_number+"目並べ」を開始します。"+Masu_number+"目並べたら勝ちです。");
        //ゲーム開始　全てのマスに0代入　0は打たれてないマスのことです。
        for(int i = 0; i < Masu_number; i++) {
            for(int j = 0; j < Masu_number; j++) {
                Masu[i][j]=0;
            }
        }
        showMasu(Masu);//盤面の状況を表示
        //試合のループ開始 turnが偶数の時は先行「○」のターン。turnが奇数の時は後攻「×」のターンである。　　
        for(int turn = 0; ;turn++){
            Masu = turnwork(turn,Masu);//先行、後攻の入出力まとめ。
            //勝ちか引き分けと判定したらゲーム終了
            if (wincheck(Masu)){
                if(turn%2==0){
                    System.out.println("\r\n先行の勝利！！");
                }else{
                    System.out.println("\r\n後攻の勝利！！");
                }
                break;
            }
            if (drowcheck(Masu)){
                System.out.println("\r\n引き分けです！！");
                break;
            }
        }
    }
    //先行、後攻の同じ入力、動作をまとめました。
    public static int[][] turnwork(int nowturn,int[][] status){
        while(true){//誤入力時、入力し直す為にループ
            if(nowturn%2==0){
                System.out.println("\r\n先行「◯」のターン。横軸と縦軸の座標を入力します。1〜"+(Masu_number)+"の数字を二つ入力してください。");
            }else{
                System.out.println("\r\n後攻「×」のターン。横軸と縦軸の座標を入力します。1〜"+(Masu_number)+"の数字を二つ入力してください。");
            }
            System.out.print("横軸：");
            int x = inputInt()-1;//-1はマス目が0から開始する内部の二重配列の座標に合わせる為です。ちなみにinputIntは0が入力不可です。
            System.out.print("縦軸：");
            int y = inputInt()-1;
            if(status[y][x]==0){//打たれてない場所かの確認
                if(nowturn%2==0){
                    status[y][x]=1;//先行「◯」の値を代入
                }else{
                    status[y][x]=100;//後攻「×」の値を代入
                }
                showMasu(status);
                break;//片方のターン終了
            }else{
                System.out.print("既に打たれた場所です。入力し直してください。");
            }
        }
        return status;
    }
    //入力の関数です。文字入力は全て０になります。
    public static int inputInt(){
        int s;
        InputStreamReader isr = new InputStreamReader(System.in);
        BufferedReader br = new BufferedReader(isr);
        try{
            String buf = br.readLine();
            s = Integer.parseInt(buf);
        }catch(Exception e){
            s = 0;
        }
        if(s>Masu_number||s==0||s>=100){
            System.out.println("範囲外の数値です。入力し直してください。");
            System.out.print("数値：");
            s=inputInt();
        }
        return s;
    }
    //盤面の表示　＊Masu_numberを使用しています。
    public static void showMasu(int[][] status){
        for(int i = 0; i < Masu_number; i++) {
            System.out.print("\r\n");
            for(int j = 0; j < Masu_number; j++) {
                if(status[i][j]==0){
                    System.out.print("■ ");
                }else if(status[i][j]==1){
                    System.out.print("◯ ");
                }else if(status[i][j]==100){
                    System.out.print("× ");
                }
            }
        }
    }
    //勝敗の判定　＊Masu_numberを使用しています。
    public static boolean wincheck(int[][] status){
        //row,i行,行の判定
        int row[] = new int [Masu_number];
        for(int i = 0; i < Masu_number; i++) {
            row[i]=0;
            for(int j = 0; j < Masu_number; j++) {
                row[i] += status[i][j];
                if(row[i]==Masu_number*1){
                    return true;
                }else if(row[i]==Masu_number*100){
                    return true;
                }
            }
        }
        //col,j列,列の判定
        int col[] = new int [Masu_number];
        for(int j = 0; j < Masu_number; j++) {
            col[j]=0;
            for(int i = 0; i < Masu_number; i++) {
                col[j] += status[i][j];
                if(col[j]== Masu_number*1){
                    return true;
                }else if(col[j]==Masu_number*100) {
                    return true;
                }
            }
        }
        //斜めの判定その１[|]左上から右下への斜めの判定
        int nanameSum=0;
        for(int i = 0; i < Masu_number; i++) {
            for(int j = 0; j < Masu_number; j++) {
                if(i==j){//左上から右下への斜めの判定
                    nanameSum += status[i][j];
                    if(nanameSum==Masu_number*1){
                        return true;
                    }else if(nanameSum==Masu_number*100){
                        return true;
                    }
                }
            }
        }
        //斜めの判定その２[/]左上から右下への斜めの判定
        nanameSum=0;
        for(int i = 0; i < Masu_number; i++) {
            for(int j = 0; j < Masu_number; j++) {
                if(i==Masu_number-j-1){//右上から左下への斜めの判定
                    nanameSum += status[i][j];
                    if(nanameSum==Masu_number*1){
                        return true;
                    }else if(nanameSum==Masu_number*100){
                        return true;
                    }
                }
            }
        }
        return false;
    }
    //引き分けの判定
    public static boolean drowcheck(int[][] status){
        for(int i = 0; i < Masu_number; i++) {
            for(int j = 0; j < Masu_number; j++) {
                if(status[i][j]==0){
                    return false;
                }
            }
        }
        return true;
    }
}
