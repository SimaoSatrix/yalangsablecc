/*
 * Simão Ramos(29035) João Martins(27396) Compiladores 2014-2015
 */
package compiladores2;

import java.lang.*;
import java.io.*;
import java.util.*;

import compiladores2.parser.*;
import compiladores2.lexer.*;
import compiladores2.node.*;

public class Main {

    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("\nUsage:");
            System.out.println(" make run ARGS= ficheiro.ya");
        } else {

            try {
                System.out.println("\n\n------------------------------------------------");
                System.out.println("          Resultado da compilação\n\n               ");
                File f = new File(args[0]);
                Parser p = new Parser(new Lexer(new PushbackReader(new FileReader(f), (int) f.length())));
                Start start = p.parse();

                AnalisadorSemantico an = new AnalisadorSemantico();
                start.apply(an);
                start.apply(new APTInterpretador());
                System.out.println("Sucesso\n\n");
                System.out.println("------------------------------------------------\n\n\n");

            } catch (Exception e) {
                System.out.println("Falhou\n\n");
                e.printStackTrace();
            }
        }
    }

};
