package board;

public enum Turno {
  BRANCO, PRETO;

  public Turno proximo(){
    if (this == BRANCO) {
        return PRETO;
    } else {
        return BRANCO;
    }
  }
}