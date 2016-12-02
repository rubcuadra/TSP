package com.company;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.*;

public class Main
{
    private static DecimalFormat df2 = new DecimalFormat(".##");

    private static final String small_path="src/com/company/P4tsp10.txt";
    private static final String big_path="src/com/company/P4tsp25.txt";

    private static int totalCities;                 //Sirve para crear la matrix
    private static double adjacency_matrix[][];     //Asi no estamos re calculando distancias
    private static ArrayList<City> cities ;         //Lista de todas las ciudades

    private static City startCity;                  //Sacamos aleatoriamente un inicio

    //<Ciudad,Lista<Ciudades> -> Path
    private static HashMap<Key,Path> memoizations = new HashMap<>(); //Programacion Dinamica

    public static void main(String[] args)
    {
        int type = 0; //0 = Recursive else is Memoization
        cities = new ArrayList<City>();
        setValues(small_path);
        startCity = cities.remove((int)Math.random()*cities.size()); //Sacamos el inicio

        int start = (int) System.currentTimeMillis();
        Path result;
        switch (type)
        {
            case 0:
                result = gPath( startCity, cities );
                break;
            default:
                result = getMemoizationPath( startCity , cities );
                break;
        }
        int _final = (int) (System.currentTimeMillis() - start);
        System.out.println( result );
        System.out.println("Tardo: "+(double)+_final/1000+" Segundos");
    }

    private static Path getMemoizationPath(City from, List<City> passThru)
    {
        Key k = new Key(from, new HashSet<City>(passThru));
        if (memoizations.containsKey( k ) ) return memoizations.get(k);

        if (passThru.isEmpty()) //vacio mandamos la distancia origen al from
        {
            Path n = new Path();    //Camino del origen al siguiente
            n.add( from , adjacency_matrix[from.getCityNumber()][startCity.getCityNumber()] );
            return n;
        }

        List<Path> path_csts = new ArrayList<>(); //Posibles caminos
        for (int i = 0; i < passThru.size(); i++)
        {
            City current_city = passThru.remove(i); //Ver la mejor forma de llegar a esta ciudad,la popeamos
            Path current = new Path();                    //Creamos el camino
            current.add(current_city ,adjacency_matrix[from.getCityNumber()][current_city.getCityNumber()]);
            current.join( getMemoizationPath(current_city,passThru) ); //Unimos con el mejor camino de los que debe pasar
            path_csts.add( current );                     //Lo agregamos como solucion

            passThru.add(i,current_city);                 //La regresamos para seguir iterando
        }
        Path min = Collections.min(path_csts);
        memoizations.put(k, min); //Lo seteamos con la llave Ciudad-Porlasquepasa
        return min;   //Regresar el camino con menor costo
    }
    private static Path gPath(City from, List<City> passThru)
    {
        if (passThru.isEmpty()) //vacio mandamos la distancia origen al from
        {
            Path n = new Path();    //Camino del origen al siguiente
            n.add( from , adjacency_matrix[from.getCityNumber()][startCity.getCityNumber()] );
            return n;
        }
        List<Path> path_csts = new ArrayList<>(); //Posibles caminos
        for (int i = 0; i < passThru.size(); i++)
        {
            City current_city = passThru.remove(i); //Ver la mejor forma de llegar a esta ciudad,la popeamos
            Path current = new Path();                    //Creamos el camino
            current.add(current_city ,adjacency_matrix[from.getCityNumber()][current_city.getCityNumber()]);
            current.join( gPath(current_city,passThru) ); //Unimos con el mejor camino de los que debe pasar
            path_csts.add( current );                     //Lo agregamos como solucion

            passThru.add(i,current_city);                 //La regresamos para seguir iterando
        }
        return Collections.min(path_csts);   //Regresar el camino con menor costo
    }
    private static boolean setValues(String p) //los lee del archivo
    {
        boolean firstLine = true;
        int cityNum = 0;
        try (Scanner scanner = new Scanner(new File(p)))
        {
            while (scanner.hasNext())
            {
                if (firstLine)
                {
                    String first_line = scanner.nextLine();
                    //totalCities = Integer.parseInt(first_line);
                    firstLine = false;
                }
                else
                {
                    String[] temp = scanner.nextLine().split(" ");
                    cities.add(new City(cityNum++,Double.parseDouble(temp[0]),Double.parseDouble((temp[1]))));
                }
            }
        } catch(IOException e)
        {
            e.printStackTrace();
            return false;
        }
        fillMatrix();
        return true;
    }
    public static void fillMatrix()
    {
        totalCities = cities.size();
        adjacency_matrix  = new double[totalCities][totalCities];

        for (int i = 0; i < totalCities; i++)   //Armamos la matrix
        {
            for (int j = 0; j < totalCities; j++)
            {
                adjacency_matrix[i][j] = City.distanceBetween(cities.get(i),cities.get(j)) ;
            }
        }

        for (int i = 1; i < totalCities; i++) //Not sure why, ese 1 no deberia ir ahi
        {
            for (int j = 1; j < totalCities; j++)
            {
                if (adjacency_matrix[i][j] == 1 && adjacency_matrix[j][i] == 0)
                {
                    adjacency_matrix[j][i] = 1;
                }
            }
        }

        printMatrix();
    }
    public static void printMatrix()
    {
        for (int i = 0; i < adjacency_matrix.length; i++) //PRINT
        {
            for (int j = 0; j < adjacency_matrix.length; j++)
            {
                System.out.print(df2.format(adjacency_matrix[j][i])+" ");
            }
            System.out.println();
        }
    }
}