/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compiladores2;

import compiladores2.analysis.DepthFirstAdapter;
import compiladores2.node.*;
import compiladores2.parser.Parser;
import compiladores2.parser.ParserException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Satrix
 */
public class AnalisadorSemantico extends DepthFirstAdapter {

    Hashtable symbol_table = new Hashtable();

    public Hashtable getSymbolTable() {
        return symbol_table;
    }

    public void outAVariavelExp(AVariavelExp node) {
        Node no = node;
        TVariavel variavel = node.getVariavel();
        try {
            while (!(no instanceof APrograma)) {

                if (no instanceof AEnumeradaDecl) {
                    AEnumeradaDecl a = (AEnumeradaDecl) no;
                    char[] chararray = a.getL().toString().toCharArray();
                    ArrayList<String> list = new ArrayList<String>();
                    String temp = "";
                    for (int i = 0; i < chararray.length; i++) {
                        if (chararray[i] == ' ') {
                            list.add(temp);
                            temp = "";
                        } else {
                            temp += chararray[i];
                        }
                    }

                    for (String s : list) {
                        if (symbol_table.contains(s)) {
                            throw new SemanticErrorException("Erro em (" + variavel.getLine() + ":" + variavel.getPos() + ") "
                        + "variavel --" + variavel.getText() + "-- já declarada previamente");
                        } else {
                            symbol_table.put(s, a.getR().toString());
                        }
                    }
                    return;
                }

                no = no.parent();
            }
        } catch (SemanticErrorException ex) {
            ex.printStackTrace();
            System.exit(0);
        }
    }

    public void outAFuncnormalFunc(AFuncnormalFunc node) {
        super.outAFuncnormalFunc(node); 
        String func = node.getL().toString();
        String[] funcSplit = func.split(" ");
        Hashtable funcSymbol_table = new Hashtable();
        symbol_table.put(funcSymbol_table, func.split(" ")[0]);
        int i = 0;
        for (i = 1; i < funcSplit.length - 1; i += 2) {
            funcSymbol_table.put(funcSplit[i], funcSplit[i + 1]);
        }
        symbol_table.put(funcSplit[0], funcSplit[i]);

        String[] funcSplitCorp = node.getR().toString().split(" ");

        for (i = 0; i < funcSplitCorp.length - 1; i += 2) {
            funcSymbol_table.put(funcSplitCorp[i], funcSplitCorp[i + 1]);
        }
    }

   public void outAAfectacaoInst(AAfectacaoInst node) {
        super.outAAfectacaoInst(node);
        String[] variaveis = node.toString().split(" ");
        String tipo = null;

        Node newAfec = node;

        while (newAfec != null || newAfec instanceof AFuncnormalFunc) {
            newAfec = newAfec.parent();
        }
        Hashtable hash = null;
        if (newAfec instanceof AFuncnormalFunc) {
            AFuncnormalFunc func = (AFuncnormalFunc) newAfec;
            String[] funcSplit = func.getL().toString().split(" ");
            int i = funcSplit.length - 1;
            Iterator<Map.Entry<Object, Object>> it = symbol_table.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry<Object, Object> entry = it.next();
                if (entry.getValue().equals(funcSplit[0])) {
                    hash = (Hashtable) entry.getKey();
                }
            }

        }
        for (String s : variaveis) {
            try {

                boolean bf = false;
                for (char c : s.toCharArray()) {
                    if (c == '.') {
                        bf = true;
                    }
                }

                float f = new Float(s);

                if (bf) {
                    if (tipo == null) {
                        tipo = "float";
                    } else if (!tipo.trim().equals("float")) {
                        System.err.println("tipos incompativeis, var:" + s + " nao aparenta ser do tipo da expressao");
                    }
                } else {
                    if (tipo == null) {
                        tipo = "int";
                    } else if (!tipo.trim().equals("int")) {
                        System.err.println("tipos incompativeis, var:" + s + " nao aparenta ser do tipo da expressao");
                    }
                }
            } catch (Exception e) {
                String t = (String) symbol_table.get(s);
                if (t != null) {
                    t = t.trim();
                }
                if (s != null) {

                    Boolean bool = new Boolean(s);
                    if (bool && tipo == null) {
                        tipo = "bool";
                    } else if (tipo != null && tipo.trim().equals("bool") && bool) {
                        //do nothing, tipo ja existe
                    } else if (tipo == null && symbol_table.containsKey(s)) {
                        tipo = (String) symbol_table.get(s);
                    } else if (tipo == null && hash != null && hash.containsKey(s)) {
                        tipo = (String) symbol_table.get(s);
                    } else if (tipo == null) {
                        tipo = "string";
                    } else if (tipo.equals("string")) {
                        //do nothing, tipo ja existe
                    } else if (t != null && !t.equals(tipo.trim())) {

                        System.err.println("Erro sintatico, tipos incompativeis na variavel:" + s);
                        System.exit(0);
                    }

                }
            }
        }
    }

    public void outAIdSimplesAfect(AIdSimplesAfect node) {
        TVariavel variavel = node.getVariavel();

        try {
            if (!(symbol_table.containsKey(variavel.getText()))) {
                throw new SemanticErrorException("Erro em (" + variavel.getLine() + ":" + variavel.getPos() + ") "
                        + "variavel --" + variavel.getText() + "-- nao declarada");
            }
        } catch (SemanticErrorException ex) {
            ex.printStackTrace();
            System.exit(0);
        }

    }

    public void outATrueExp(ATrueExp node) {
        TTrue identificadorNo = node.getTrue();
        try {
            Node no = node;
            while (!(no instanceof APrograma)) {

                if (no instanceof AEnumeradaDecl) {
                    throw new SemanticErrorException("Erro em (" + identificadorNo.getLine() + ":" + identificadorNo.getPos() + ") "
                            + "booleano não permitido como variavel");
                }

                no = no.parent();
            }
        } catch (SemanticErrorException ex) {
            ex.printStackTrace();
            System.exit(0);

        }
    }

    public void outAFalseExp(AFalseExp node) {
        TFalse identificadorNo = node.getFalse();
        try {
            Node no = node;
            while (!(no instanceof APrograma)) {

                if (no instanceof AEnumeradaDecl) {
                    throw new SemanticErrorException("Erro em (" + identificadorNo.getLine() + ":" + identificadorNo.getPos() + ") "
                            + "booleano não permitido como variavel");
                }

                no = no.parent();
            }
        } catch (SemanticErrorException ex) {
            ex.printStackTrace();
            System.exit(0);

        }
    }

    public void outAIntExp(AIntExp node) {
        TInteger identificadorNo = node.getInteger();
        try {
            Node no = node;
            while (!(no instanceof APrograma)) {

                if (no instanceof AEnumeradaDecl) {
                    throw new SemanticErrorException("Erro em (" + identificadorNo.getLine() + ":" + identificadorNo.getPos() + ") "
                            + "inteiro não permitido como variavel");
                }

                no = no.parent();
            }
        } catch (SemanticErrorException ex) {
            ex.printStackTrace();
            System.exit(0);
        }
    }

    public void outAFloatExp(AFloatExp node) {
        TFloatProd identificadorNo = node.getFloatProd();
        try {
            Node no = node;
            while (!(no instanceof APrograma)) {

                if (no instanceof AEnumeradaDecl) {
                    throw new SemanticErrorException("Erro em (" + identificadorNo.getLine() + ":" + identificadorNo.getPos() + ") "
                            + "float não permitido como variavel");
                }

                no = no.parent();
            }
        } catch (SemanticErrorException ex) {
            ex.printStackTrace();
            System.exit(0);
        }
    }

    public void outANominalExp(ANominalExp node) {
        TStringProd identificadorNo = node.getStringProd();
        try {
            Node no = node;
            while (!(no instanceof APrograma)) {

                if (no instanceof AEnumeradaDecl) {
                    throw new SemanticErrorException("Erro em (" + identificadorNo.getLine() + ":" + identificadorNo.getPos() + ") "
                            + "string não permitida como variavel");
                }

                no = no.parent();
            }
        } catch (SemanticErrorException ex) {
            ex.printStackTrace();
            System.exit(0);

        }
    }

    public void outAChamadaFuncaoChamada(AChamadaFuncaoChamada node) {
        boolean declaracao = false;
        TVariavel identificadorNo = node.getVariavel();
        try {
            Node no = node;
            while (!(no instanceof APrograma)) {
                if (no instanceof AEnumeradaDecl) {
                    declaracao = true;
                }
                if (declaracao == true && (no instanceof ADeclaracaoInst)) {
                    throw new SemanticErrorException("Erro em (" + identificadorNo.getLine() + ":" + identificadorNo.getPos() + ") "
                            + "chamada de funcao não permitida como variavel");
                }

                no = no.parent();
            }
        } catch (SemanticErrorException ex) {
            ex.printStackTrace();
            System.exit(0);
        }
    }

    public void outAChamadaFuncaoCompChamada(AChamadaFuncaoCompChamada node) {
        boolean declaracao = false;
        TVariavel identificadorNo = node.getL();
        try {
            Node no = node;
            while (!(no instanceof APrograma)) {
                if (no instanceof AEnumeradaDecl) {
                    declaracao = true;
                }
                if (declaracao == true && (no instanceof ADeclaracaoInst)) {
                    throw new SemanticErrorException("Erro em (" + identificadorNo.getLine() + ":" + identificadorNo.getPos() + ") "
                            + "chamada de funcao não permitida como variavel");
                }

                no = no.parent();
            }
        } catch (SemanticErrorException ex) {
            ex.printStackTrace();
            System.exit(0);
        }
    }

    public void outAIniArrayExp(AIniArrayExp node) {
        try {
            Node no = node;
            while (!(no instanceof APrograma)) {

                if (no instanceof AEnumeradaDecl && (no.parent() instanceof ACiniciDecl)) {
                    throw new SemanticErrorException("Erro em  "
                            + "Array a ser usado como variavel");
                }

                no = no.parent();
            }
        } catch (SemanticErrorException ex) {
            ex.printStackTrace();
            System.exit(0);
        }
    }

    public void outABreakInst(ABreakInst node) {
        boolean dentro_ciclo = false;
        TBreak identificadorNo = node.getBreak();
        try {

            Node no = node;
            while (!(no instanceof APrograma)) {

                if (no instanceof ACicloInst) {
                    dentro_ciclo = true;
                    break;
                }

                no = no.parent();
            }
            if (!dentro_ciclo) {
                throw new SemanticErrorException("Erro em (" + identificadorNo.getLine() + ":" + identificadorNo.getPos() + ") "
                        + "break fora de while");
            }
        } catch (SemanticErrorException ex) {
            ex.printStackTrace();
            System.exit(0);
        }
    }

    public void outANextInst(ANextInst node) {
        boolean dentro_ciclo = false;
        TNext identificadorNo = node.getNext();
        try {

            Node no = node;
            while (!(no instanceof APrograma)) {

                if (no instanceof ACicloInst) {
                    dentro_ciclo = true;
                    break;
                }

                no = no.parent();
            }
            if (!dentro_ciclo) {
                throw new SemanticErrorException("Erro em (" + identificadorNo.getLine() + ":" + identificadorNo.getPos() + ") "
                        + "next fora de while");
            }
        } catch (SemanticErrorException ex) {
            ex.printStackTrace();
            System.exit(0);
        }
    }

    public void outACondicionalInst(ACondicionalInst node) {
        boolean dentro_funcao = false;

        try {

            Node no = node;
            while (!(no instanceof APrograma)) {

                if (no instanceof AFuncaoInst) {
                    dentro_funcao = true;
                    break;
                }

                no = no.parent();
            }
            if (!dentro_funcao) {
                throw new SemanticErrorException("Erro em stamement if "
                        + "encontra-se fora de funcao");
            }
        } catch (SemanticErrorException ex) {
            ex.printStackTrace();
            System.exit(0);
        }
    }

    public void outACicloInst(ACicloInst node) {
        boolean dentro_funcao = false;

        try {

            Node no = node;
            while (!(no instanceof APrograma)) {

                if (no instanceof AFuncaoInst) {
                    dentro_funcao = true;
                    break;
                }

                no = no.parent();
            }
            if (!dentro_funcao) {
                throw new SemanticErrorException("Erro em ciclo while "
                        + "encontra-se fora de funcao");
            }
        } catch (SemanticErrorException ex) {
            ex.printStackTrace();
            System.exit(0);
        }
    }

    public void outAFuncaoInst(AFuncaoInst node) {

        try {
            Node no = node;
            no = no.parent();
            while (!(no instanceof APrograma)) {

                if (no instanceof AFuncaoInst) {
                    throw new SemanticErrorException("Funcao nao pode ser declarada dentro de corpo de outra funcao");
                }

                no = no.parent();
            }
        } catch (SemanticErrorException ex) {
            ex.printStackTrace();
            System.exit(0);
        }
    }

    public void outASvoidCorpo(ASvoidCorpo node) {
        try {
            Node no = node;
            while (!(no instanceof APrograma)) {
                if (no instanceof AFuncnormalFunc) {
                    AFuncnormalFunc a = (AFuncnormalFunc) no;
                    String[] v = a.getL().toString().split(" ");
                    if (v[v.length - 1].equals("void")) {
                        throw new SemanticErrorException("Retorno em função void");
                    }
                }
                no = no.parent();
            }
        } catch (SemanticErrorException ex) {
            ex.printStackTrace();
            System.exit(0);
        }
    }

    public void outAFinalInstCorpo(AFinalInstCorpo node) {
        try {

            if (node.parent() instanceof AFuncnormalFunc) {
                Node no = node.parent();
                AFuncnormalFunc a = (AFuncnormalFunc) no;
                String[] v = a.getL().toString().split(" ");
                if (!(v[v.length - 1].equals("void"))) {
                    throw new SemanticErrorException("Ausencia de retorno em função");
                }
            }

        } catch (SemanticErrorException ex) {
            ex.printStackTrace();
            System.exit(0);
        }
    }

    public void outAVariasDeclChamada(AVariasDeclChamada node) {
        try {
            if ((node.parent() instanceof AChamadaFuncaoCompChamada) && (node.parent().parent() instanceof AChamadaStmtInst)) {
                throw new SemanticErrorException("Declaraçoes de variaveis nao podem ser argumentos de chamadas de funçoes");
            }
        } catch (SemanticErrorException ex) {
            ex.printStackTrace();
            System.exit(0);
        }
    }

    public void outAUniDeclChamada(AUniDeclChamada node) {
        try {
            if ((node.parent() instanceof AChamadaFuncaoCompChamada) && (node.parent().parent() instanceof AChamadaStmtInst)) {
                throw new SemanticErrorException("Declaraçoes de variaveis nao podem ser argumentos de chamadas de funçoes");
            }
        } catch (SemanticErrorException ex) {
            ex.printStackTrace();
            System.exit(0);
        }
    }

    @Override
    public void outAChamadaStmtInst(AChamadaStmtInst node) {

        if (!symbol_table.containsKey(node.toString().split(" ")[0])) {
            System.err.println("nao existe esta a funcao: " + node.toString().split(" ")[0]);
            System.exit(0);
        }
    }

}
