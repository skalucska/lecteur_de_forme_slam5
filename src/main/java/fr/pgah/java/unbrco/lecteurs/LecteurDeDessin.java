package fr.pgah.java.unbrco.lecteurs;

import fr.pgah.java.unbrco.model.Dessin;
import fr.pgah.java.unbrco.model.Forme;
import fr.pgah.java.unbrco.ui.EditeurDeFormes;

import java.util.ArrayList;
import java.util.List;
import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LecteurDeDessin implements ActionListener {
  private Dessin dessin;
  private Timer timer;
  private int colonneEnCours;

  private List<Forme> dansLaColonnePrecedente;
  private List<Forme> dansLaColonneCourante;

  public LecteurDeDessin(Dessin dessin, Timer timer) {
    this.dessin = dessin;
    this.timer = timer;
    colonneEnCours = 0;
    dansLaColonnePrecedente = new ArrayList<>();
    dansLaColonneCourante = new ArrayList<>();
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    selectionnerEtJouerLesFormes();
    dessinerLigneProgression();
    incrementerColonne();
    arreterSiDessinFini();
  }

  private void incrementerColonne() {
    colonneEnCours += 1;
    dansLaColonnePrecedente = dansLaColonneCourante;
  }

  private void dessinerLigneProgression() {
    dessin.setColonneCourante(colonneEnCours);
    dessin.repaint();
  }

  private void arreterSiDessinFini() {
    if (colonneEnCours > EditeurDeFormes.LONGUEUR) {
      timer.stop();
    }
  }

  private void selectionnerEtJouerLesFormes() {
    // À COMPLÉTER
    dansLaColonneCourante = dessin.formesSurLaColonne(colonneEnCours);
    // Déselectionner et stopper toutes les formes qui étaient dans
    // la colonne précédente et qui ne sont plus dans la colonne courante
    for (Forme precedente : dansLaColonnePrecedente) {
      if (!dansLaColonneCourante.contains(precedente)) {
        precedente.deselectionnerEtStopper();
      }
    }

    // Sélectionner et jouer toutes les formes qui sont dans
    // la colonne courante et qui n'étaient pas dans la colonne précédente
    for (Forme courante : dansLaColonneCourante) {
      if (!dansLaColonnePrecedente.contains(courante)) {
        courante.selectionnerEtJouer();
      }

    }
  }
}
