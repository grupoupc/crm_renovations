package zura.premium.service;

import java.util.List;

import zura.premium.model.ListarQuerysRequest;
import zura.premium.model.ListarQuerysResponse;

public interface ListarQuerysService {

	public abstract List<ListarQuerysResponse> getListarQuerys(ListarQuerysRequest listarquerys) throws Exception;
	public abstract List<ListarQuerysResponse> getIListarQuerys(ListarQuerysRequest listarquerys) throws Exception;
}
