package visao;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Zipar {	
	public static boolean compactarParaZip(final String arqSaida, final String[] arqEntradas){
	    int cont;
	    final byte[] dados = new byte[1024];

	    FileOutputStream destino = null;
	    
		try {
			destino = new FileOutputStream(new File(arqSaida));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	    final ZipOutputStream saida = new ZipOutputStream(new BufferedOutputStream(destino));

	    for (final String arqEntrada : arqEntradas) {
	        final File file = new File(arqEntrada);
	        
	        FileInputStream streamDeEntrada = null;
	        BufferedInputStream origem = null;
	        ZipEntry entry = null;
			try {
				streamDeEntrada = new FileInputStream(file);
				origem = new BufferedInputStream(streamDeEntrada, 1024);
				entry = new ZipEntry(file.getName());
				saida.putNextEntry(entry);
				
				while ((cont = origem.read(dados, 0, 1024)) != -1) {
		            saida.write(dados, 0, cont);
		        }
		        origem.close();
		        
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				
				return false;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				
				return false;
			}

	        
	    }

	    try {
			saida.close();
			
			return true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			return false;
		}
	}
}
