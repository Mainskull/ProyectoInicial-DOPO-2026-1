package dominio;

import java.util.ArrayList;

public class TowerContest
{
    public String solve(int n, int h)
    {
        ArrayList<Integer> respuesta = armar(n, h);

        if (respuesta == null) {
            return "impossible";
        }

        return pasarAlturas(respuesta);
    }

    public void simulate(int n, int h)
    {
        String resp = solve(n, h);

        if (resp.equals("impossible")) {
            System.out.println("impossible");
            return;
        }

        Tower t = new Tower(27, 27);
        t.makeVisible();

        String[] datos = resp.split(" ");
        for (int i = 0; i < datos.length; i++) {
            t.pushCup((Integer.parseInt(datos[i])+ 1)/2);
        }
    }

    private ArrayList<Integer> armar(int n, long h)
    {
        if (!sirve(n, h)) {
            return null;
        }

        ArrayList<Integer> solucion = new ArrayList<Integer>();

        if (n == 1) {
            solucion.add(1);
            return solucion;
        }

        if (h == min(n)) {
            for (int i = n; i >= 1; i--) {
                solucion.add(i);
            }
            return solucion;
        }

        if (sirve(n - 1, h - 1)) {
            ArrayList<Integer> resto = armar(n - 1, h - 1);
            if (resto != null) {
                solucion.add(n);
                for (int i = 0; i < resto.size(); i++) {
                    solucion.add(resto.get(i));
                }
                return solucion;
            }
        }

        long pedazo = h - min(n);

        for (int usados = 1; usados < n; usados++) {
            if (sirve(usados, pedazo)) {
                ArrayList<Integer> primero = armar(usados, pedazo);

                if (primero != null) {
                    for (int i = 0; i < primero.size(); i++) {
                        solucion.add(primero.get(i));
                    }

                    solucion.add(n);

                    for (int i = n - 1; i > usados; i--) {
                        solucion.add(i);
                    }

                    return solucion;
                }
            }
        }

        return null;
    }

    private boolean sirve(int n, long h)
    {
        if (n < 1) {
            return false;
        }

        return h >= min(n) && h <= max(n) && h != max(n) - 2;
    }

    private long min(int n)
    {
        return 2L * n - 1;
    }

    private long max(int n)
    {
        return 1L * n * n;
    }

    private String pasarAlturas(ArrayList<Integer> datos)
    {
        String s = "";

        for (int i = 0; i < datos.size(); i++) {
            long alt = 2L * datos.get(i) - 1;

            if (i == 0) {
                s = "" + alt;
            }
            else {
                s = s + " " + alt;
            }
        }

        return s;
    }
}
