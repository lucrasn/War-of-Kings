package board;
import board.Turno;
import board.Tabuleiroxz_IF;

public class ClassicoDois implements Tabuleiroxz_IF {  
  private Turno turnoAtual = Turno.BRANCO; //pois sempre as brancas começam

  public void moverPeca(){
    //aqui vai ficar a lógica para movimentação
    
    turnoAtual = turnoAtual.proximo(); //depois do movimento
  }

}
