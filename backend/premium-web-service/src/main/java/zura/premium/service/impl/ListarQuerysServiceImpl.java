package zura.premium.service.impl;

import java.util.ArrayList;
import java.util.List;


import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.ParameterMode;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.StoredProcedureQuery;

import org.springframework.stereotype.Service;

import zura.premium.model.ListarQuerysRequest;
import zura.premium.model.ListarQuerysResponse;
import zura.premium.service.ListarQuerysService;

@Service
public class ListarQuerysServiceImpl implements ListarQuerysService {		
	@PersistenceContext
	private EntityManager entityManager;
	private String[] Filas;
	
	public List<ListarQuerysResponse> getListarQuerys(ListarQuerysRequest listarquerys) throws Exception {
	try {						
			List<Object[]> dataProcedureBD = null;		
				
			StoredProcedureQuery query = entityManager.createStoredProcedureQuery("sp_Trae_ListarQuerys");
			query.registerStoredProcedureParameter(1, String.class, ParameterMode.IN);
			
			
			String strdatos = listarquerys.getOpcionListarQuerys();
			
			System.out.println(strdatos);
			
			String[] arrparamc =strdatos.split("░");  
					
			String cmd = "";
			if (listarquerys.getOpcionListarQuerys().indexOf("░")>0) {
				cmd =  arrparamc[1];				
			}else {			
				cmd = listarquerys.getOpcionListarQuerys();				
			}		
						
			query.setParameter(1, cmd.toString());
	
			query.execute();
					
			dataProcedureBD = query.getResultList();
			String comando = "";			
			
			for (Object[] row : dataProcedureBD) {						
				comando = row[1] == null ? "" : row[1].toString();
			}		
			System.out.println(strdatos);		
			return getListarMaestro(comando, arrparamc);
		}catch(Exception e){		
			System.out.println("Aplicando Fila de Query Resulset" + e.getMessage());
			ListarQuerysResponse listarquerysResponse = null;
			List<Object[]> dataProcedureBD = null;
			List<ListarQuerysResponse> listListarQuerys = new ArrayList<>();
			listarquerysResponse = new ListarQuerysResponse();
			Filas = new String[2];
			Filas[0] ="9999";
			Filas[1] ="No existe datos para mostrar: "+e.getMessage();
			listarquerysResponse.setColumnas(Filas);
	        listListarQuerys.add(listarquerysResponse);
	        return listListarQuerys;
		}
	}

	
	public List<ListarQuerysResponse> getListarMaestro(String comando, String[] arrparamc) throws Exception {
		ListarQuerysResponse listarquerysResponse = null;
		List<Object[]> dataProcedureBD = null;
		List<ListarQuerysResponse> listListarQuerys = new ArrayList<>();
		if(comando=="") {
			listarquerysResponse = new ListarQuerysResponse();
			Filas = new String[2];
			Filas[0] ="9999";
			Filas[1] ="Error no existe procedimiento en la lista de querys consulte con su administrador";
			listarquerysResponse.setColumnas(Filas);			
			listListarQuerys.add(listarquerysResponse);
		}else {			
			dataProcedureBD = null;					
			
			String[] arrparam = null;
			String[] arrparamr= null;			
							
			String cmd = comando;									
			
			if (comando.indexOf(":")>-1) {				
				arrparam = comando.split(":");
				arrparamr = arrparamc[2].split("¬");				
				cmd = arrparamc[0] + "."  + arrparam[0].replace("'", "").replace(" ", "");				
			}		
			else {
				cmd = arrparamc[0] + "." + cmd; 
			}						
			
			StoredProcedureQuery query = entityManager.createStoredProcedureQuery(cmd.toString());								
			System.out.println("Aplicando Parametros");								
			System.out.println("====================");
			if (comando.indexOf(":")>-1) {				
				
				for (int i = 0; i < arrparam.length-1; i = i+1) {
					if (arrparam[i+1].indexOf("INOUT")>-1) {										
						query.registerStoredProcedureParameter(i+1, String.class, ParameterMode.INOUT);						
						String valor = arrparamr[i].replace("¬", "").trim();						
						query.setParameter(i+1, valor);						
						System.out.println(i+1 +" : "+ valor);
					}else {
						if (arrparam[i+1].indexOf("OUT")>-1) {						
							query.registerStoredProcedureParameter(i+1, String.class, ParameterMode.OUT);						
						}else {														
							query.registerStoredProcedureParameter(i+1, String.class, ParameterMode.IN);
							String valor = arrparamr[i].replace("¬", "").trim();						
							query.setParameter(i+1, valor);
							System.out.println(i+1 +" : "+ valor);
						}
					}
				}						
			}
						
			query.execute();						
			
			String outMsj ="";
			int cont = 0;
			System.out.println("Aplicando Resultado de Query Resulset");				
			dataProcedureBD = query.getResultList();			
			return getRespuesta(dataProcedureBD);	
		}					
		return listListarQuerys;
	}
		
	public List<ListarQuerysResponse> getRespuesta(List<Object[]> dataProcedureBD) throws Exception {
			ListarQuerysResponse listarquerysResponse = null;			
			List<ListarQuerysResponse> listListarQuerys = new ArrayList<>();			
					System.out.println("Aplicando Resultado de Query Resulset");									
						if (dataProcedureBD.isEmpty()){						
							listarquerysResponse = new ListarQuerysResponse();
							Filas = new String[2];
							Filas[0] ="9999";
							Filas[1] ="No existe datos para mostrar";
							listarquerysResponse.setColumnas(Filas);
					        listListarQuerys.add(listarquerysResponse);
						}else {													
							for (Object[] row : dataProcedureBD) {													
								listarquerysResponse = new ListarQuerysResponse();														
								listarquerysResponse.setColumnas(row);   				
								listListarQuerys.add(listarquerysResponse);
							}	
						}				
			return listListarQuerys;
		}


	@Override
	public List<ListarQuerysResponse> getIListarQuerys(ListarQuerysRequest listarquerys) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
		
}