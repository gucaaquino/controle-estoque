package visao;
import java.io.File;
import java.util.ArrayList;

import javax.swing.JOptionPane;

public class BuscaArquivo {
	public ArrayList buscarArquivoPorNome(String palavra, String caminhoInicial){
        ArrayList lista = new ArrayList();
        try{
            File arquivo = new File(caminhoInicial);
            lista =  buscar(arquivo, palavra, lista);        
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Caminho Inválido");
        }
        return lista;
    }

    public ArrayList buscar(File arquivo, String palavra, ArrayList lista) {
        if (arquivo.isDirectory()) {
            File[] subPastas = arquivo.listFiles();
            for (int i = 0; i < subPastas.length; i++) {
                lista = buscar(subPastas[i], palavra, lista);
                if (arquivo.getName().equalsIgnoreCase(palavra)) lista.add(arquivo.getAbsolutePath());
                else if (arquivo.getName().indexOf(palavra) > -1) lista.add(arquivo.getAbsolutePath());
            }
        }
        else if (arquivo.getName().equalsIgnoreCase(palavra)) lista.add(arquivo.getAbsolutePath());
        else if (arquivo.getName().indexOf(palavra) > -1) lista.add(arquivo.getAbsolutePath());
        return lista;
    }
}
