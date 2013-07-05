import java.util.ArrayList;
import java.util.List;

public class Vitrine {
	
	private static List<Produto> produtos;
	
	public Vitrine() {
		produtos = new ArrayList<Produto>();
	}
	
	public void addProdutos(Produto... ps) {
		for (Produto p : ps)
			produtos.add(p);
	}
	
	public List<Produto> getProdutos() {
		return produtos;
	}

}
