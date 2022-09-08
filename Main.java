package Lesson14;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Main {

    //создание колоды
    static void createDeck(ArrayList<Integer> fdeck){
        for (int k = 0; k < 4; k++) {
            for (int i = 1; i < 9; i++) {
                fdeck.add(i);
            }
            for (int i = 0; i < 4; i++) {
                fdeck.add(10);
            }
        }
    }

    //функция перетасовка колоды
    static void shuffleDeck(ArrayList<Integer> fdeck){
        Random gen = new Random();
        for (int i = 0; i < fdeck.size(); i++) {
            int randomIndex=gen.nextInt(fdeck.size());   //или gen.nextInt(52);
            int temp = fdeck.get(i);
            fdeck.set(i, fdeck.get(randomIndex));
            fdeck.set(randomIndex, temp);
        }
    }

//    //функция перетасовка колоды УЛУЧШЕННАЯ
//    static void shuffleDeck(ArrayList<Integer> fdeck){
//        Random gen = new Random();
//        for (int i = 0; i < fdeck.size()-1; i++) {
//            int randomIndex=gen.nextInt(fdeck.size()-(i+1)+(i+1);   //или gen.nextInt(52);
//            int temp = fdeck.get(i);
//            fdeck.set(i, fdeck.get(randomIndex));
//            fdeck.set(randomIndex, temp);
//        }
//    }

//    static void playerTurn(ArrayList<Integer> fdeck, ArrayList<Integer> fplayer){
//        //игрок по желаению берёт карты из колоды
//        Scanner in = new Scanner (System.in);
//        int answer = 1;
//        do {
//            //взять карту из колоды
//            int card = fdeck.get(fdeck.size() - 1);
//            //удалить взятую карту из колоды
//            fdeck.remove(fdeck.size() - 1);
//            //добавить эту карту игроку
//            fplayer.add(card);
//            //подсчитать сколько у игрок очков и выдать на экран
//            int sum = 0;
//            for (int i = 0; i < fplayer.size(); i++) {
//                sum +=fplayer.get(i);
//            }
//            if (sum >= 21) break;
//            System.out.println("Хотите взять ещё одну карту? 1-да, любая другая цифра - нет");
//            answer = in.nextInt();
//        } while(answer == 1);
//    }

    static void playerTurn(ArrayList<Integer> fdeck, ArrayList<Integer> fplayer){
        //игрок по желаению берёт карты из колоды
        Scanner in = new Scanner (System.in);
        int answer = 1;
        do {
            //игрок берет одну карту из колоды себе
            playerTakeOneCard(fdeck, fplayer);
            //подсчитать склоько у игрока очков и выдать на экран
            int playerScore = calcScore(fplayer);
            System.out.println("У вас " + playerScore + " очков");
            if (playerScore >= 21) break;
            else {
                System.out.println("Хотите взять ещё одну карту? 1-да, любая другая цифра - нет");
                answer = in.nextInt();
            }
        } while(answer == 1);
    }

    static void playerTakeOneCard(ArrayList<Integer> fdeck, ArrayList<Integer> fplayer){
        //взять карту из колоды
        int card = fdeck.get(fdeck.size() - 1);
        //удалить взятую карту из колоды
        fdeck.remove(fdeck.size() - 1);
        //добавить эту карту игроку
        fplayer.add(card);
    }

    static int calcScore(ArrayList<Integer> fplayer){
        //подсчитать сколько у игрок очков и выдать на экран
        int sum = 0;
        for (int i = 0; i < fplayer.size(); i++) {
            sum +=fplayer.get(i);
        }
        return sum;
    }

    static void dilerTurn(ArrayList<Integer> fdeck, ArrayList<Integer> fdiler){
        //дилер по желаению берёт карты из колоды
        Scanner in = new Scanner (System.in);
        do {
            //дилер берет одну карту из колоды себе
            playerTakeOneCard(fdeck, fdiler);
            //подсчитать склоько у дилера очков и выдать на экран
            int dilerScore = calcScore(fdiler);
            if (dilerScore >= 17) break;
        } while(calcScore(fdiler) <=17);
    }

    public static void main(String[] args) {

        //создать новую колоду
        final int deckSize = 52;
        ArrayList <Integer> deck = new ArrayList<>();
        createDeck(deck);

        //перетасовать колоду
        shuffleDeck(deck);

        //создать игрока
        ArrayList<Integer> player = new ArrayList<>();

        //создать дилера
        ArrayList<Integer> dealer = new ArrayList<>();

        //==действия игрока
        playerTurn(deck, player);
        // Проверка результатов действий игрока
        //1. Игрок наблра 21, игра заканчивается, игрок выиграл
        if (calcScore(player) == 21) {
            System.out.println("Игрок выиграл. Конец игры");
            return;
        }
        //2. Игрок набрал больше 21, игра заканчивается, игрок проиграл
        if (calcScore(player) > 21) {
            System.out.println("Игрок проиграл. Конец игры");
            return;
        }
        //3. Меньше 21 очка, игрок сказал хватит
        //==действия дилера
        dilerTurn(deck, dealer);
        // Проверка результатов действий дилера
        //1. Дилер наблра 21 (у игрока заведомо <21), игра заканчивается, дилер выиграл
        if (calcScore(dealer) == 21) {
            System.out.println("Дилер выиграл. Конец игры. У дилера " + calcScore(dealer) + "очков");
            return;
        }
        //2. Дилер набрал больше 21, игра заканчивается, дилер проиграл
        if (calcScore(dealer) > 21) {
            System.out.println("Дилер проиграл. Конец игры. У дилера " + calcScore(dealer) + "очков");
            return;
        }
        //3. Дилер набрал меньш 21 очка
        //3.1 У игрока больше очков чем у дилера - выиграл игрок
        if (calcScore(player) > calcScore(dealer)) {
            System.out.println("У игрок больше очков. ИГРОК ВЫИГРАЛ. Конец игры. У дилера " + calcScore(dealer) + "очков");
        }
        //3.2 У дилера больше очков, чем у игрока - выиграл дилер
        if (calcScore(player) < calcScore(dealer)) {
            System.out.println("У игрока МЕНЬШЕ очков. ИГРОК проиграл :(. Конец игры. У дилера " + calcScore(dealer) + "очков");
        }
        //3.3 У обоих поровну очков - ничья
        else {
            System.out.println("У обоих поровну очко! НИЧЬЯ :). У дилера " + calcScore(dealer) + "очков");
        }
    }
}
