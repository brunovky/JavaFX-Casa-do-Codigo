import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Carrinho {

	private static List<Produto> produtos;

	public Carrinho() {
		produtos = new ArrayList<Produto>();
	}

	public void addProdutos(Produto... ps) {
		for (Produto p : ps)
			produtos.add(p);
	}

	public void removeProduto(Produto p) {
		Iterator<Produto> itProduto = produtos.iterator();
		while (itProduto.hasNext()) {
			Produto produto = itProduto.next();
			if (produto.getProduto().equals(p.getProduto())
					&& produto.getPreco() == p.getPreco()) {
				itProduto.remove();
			}
		}
	}

	public List<Produto> getProdutos() {
		return produtos;
	}

}
