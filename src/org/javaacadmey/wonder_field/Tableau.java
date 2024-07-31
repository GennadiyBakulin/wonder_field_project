package org.javaacadmey.wonder_field;

public class Tableau {

  private final String rightAnswer;
  private final boolean[] isOpenLettersOnTableau;

  public Tableau(String rightAnswer) {
    this.rightAnswer = rightAnswer;
    isOpenLettersOnTableau = new boolean[rightAnswer.length()];
  }

  public void displayLettersOnTableau() {
    if (isNotEmptyAttributes()) {
      for (int i = 0; i < isOpenLettersOnTableau.length; i++) {
        if (isOpenLettersOnTableau[i]) {
          System.out.print(" " + String.valueOf(rightAnswer.charAt(i)).toUpperCase());
        } else {
          System.out.print(" _");
        }
      }
      System.out.println(" ");
    }
  }

  public void openLetter(String letter) {
    if (isNotEmptyAttributes()) {
      for (int i = 0; i < rightAnswer.length(); i++) {
        if (String.valueOf(rightAnswer.charAt(i)).equalsIgnoreCase(letter)) {
          isOpenLettersOnTableau[i] = true;
        }
      }
    }
  }

  public void openFullWord() {
    for (char letter : rightAnswer.toCharArray()) {
      System.out.print(" " + String.valueOf(letter).toUpperCase());
    }
    System.out.println(" ");
  }

  public boolean isContainsUnknownLetters() {
    for (boolean isLetter : isOpenLettersOnTableau) {
      if (!isLetter) {
        return true;
      }
    }
    return false;
  }

  public boolean isNotEmptyAttributes() {
    return rightAnswer != null && isOpenLettersOnTableau != null && !rightAnswer.isBlank()
        && isOpenLettersOnTableau.length != 0;
  }

  public String getRightAnswer() {
    return rightAnswer;
  }
}
