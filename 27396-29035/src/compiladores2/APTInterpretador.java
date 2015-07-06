/*
 * Simão Ramos(29035) João Martins(27396) Compiladores 2014-2015
 */
package compiladores2;

import java.util.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.tree.*;
import compiladores2.analysis.*;
import compiladores2.node.*;

public class APTInterpretador extends DepthFirstAdapter {

    private Stack pais = new Stack();

    public APTInterpretador() {
    }

    //cria uma janela JFrame com todos os elementos
    public void outStart(Start node) {
        JFrame frame = new JFrame("Output");
        JTree tree = new JTree((DefaultMutableTreeNode) pais.pop());
        JScrollPane pane = new JScrollPane(tree);

	expandirArvore(tree);

        /* window listener para matar o programa*/
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        frame.setSize(300, 400);
        frame.getContentPane().add(pane);
        frame.setVisible(true);
    }

    /*
     * Adiciona as expressoes para a stack e para cada expressao adiciona 
     * o seu operador
     */
    public void defaultIn(Node node) {
        DefaultMutableTreeNode newNode = new DefaultMutableTreeNode(node.getClass().getName().substring(node.getClass().getName().lastIndexOf('.') + 1));

        pais.push(newNode);
    }

    /*
     * Assim que deixamos um nao-terminal, o seu pai é o nó anterior 
     * na stack, entao fazemos pop e adicionamos esse no 
     */
    public void defaultOut(Node node) {
        DefaultMutableTreeNode newNode = (DefaultMutableTreeNode) pais.pop();
        ((DefaultMutableTreeNode) pais.peek()).add(newNode);
    }

    /*
     * adiciona um ramo na ramificacao
     */
    public void defaultCase(Node node) {
        DefaultMutableTreeNode newNode = new DefaultMutableTreeNode(((Token) node).getText());
        ((DefaultMutableTreeNode) pais.peek()).add(newNode);
    }

    public void caseEOF(EOF node) {
    }
    
    /*
     * ira expandir todos o ramos da arvore para garantir que eles estejam visiveis quando executados
     */
    public static void expandirArvore(JTree tree) {
        Object root = tree.getModel().getRoot();

        if (root != null) {
            expandirArvore(tree, new TreePath(root));
        }
    }
    
    /**percore o metodo sobre caminhos possiveis da arvore. 
     * Para por cada ciclo percorrido ele ira adicionar ou nao caminhos
     * na collecao de caminhoPossiveis de modo a percorrer todos os possiveis das 
     * subarvores
     * 
     * @param tree arvore JTree.
     * @param caminho raizInicial.
     */
    
    public static void expandirArvore(JTree arvoreJ, TreePath caminho) {
        for (Iterator i = caminhosPossiveis(arvoreJ.getModel(), caminho, new HashSet()).iterator(); i.hasNext();) {
            arvoreJ.expandPath((TreePath) i.next());
        }
    }

    /**
     * Precorre os caminhos possiveis da arvore.
     * Quando os nos da arvore sao leaf este ira retornar uma collection fazia caso contrario
     * caso contrario retornara devolve os caminhos possiveis da subarvore.
     */
    public static Collection caminhosPossiveis(TreeModel treeModel, TreePath caminho, Collection caminhosPossiveis) {
        
        caminhosPossiveis.clear();
        if (treeModel.isLeaf(caminho.getLastPathComponent())) {
            return caminhosPossiveis; // should really be forbidden (?)
        }

        caminhosPossiveisImp(treeModel, caminho, caminhosPossiveis);

        return caminhosPossiveis;
    }
    
    private static void caminhosPossiveisImp(TreeModel treeModel, TreePath caminho, Collection caminhos) {
        Object node = caminho.getLastPathComponent();

        boolean temFilhosLeaf = false;

        int count = treeModel.getChildCount(node);

        for (int i = 0; i < count; i++) {
            if (!treeModel.isLeaf(treeModel.getChild(node, i))) {
                temFilhosLeaf = true;
            }
        }

        if (!temFilhosLeaf) {
            caminhos.add(caminho);
        } else {
            for (int i = 0; i < count; i++) {
                Object obj = treeModel.getChild(node, i);

                if (!treeModel.isLeaf(obj)) {
                    caminhosPossiveisImp(treeModel, caminho.pathByAddingChild(obj), caminhos);
                }
            }
        }
    }
}
