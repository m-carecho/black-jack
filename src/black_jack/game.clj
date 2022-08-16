(ns black-jack.game
  (:require [card-ascii-art.core :as card]))

(defn new-card []
  "Generates a card number between 1 and 13"
  (inc (rand-int 13)))

(defn JQK-to-10 [card]
  (if (> card 10) 10 card))

(defn A-to-11 [card]
  (if (= card 1) 11 card))

(defn points-cards [cards]
  (let [cards-without-JQK (map JQK-to-10 cards)
        cards-with-A11 (map A-to-11 cards-without-JQK)
        points-with-A1(reduce + cards-without-JQK)
        points-with-A11(reduce + cards-with-A11)]
  (if (> points-with-A11 21) points-with-A1 points-with-A11)))

(defn player [player-name]
  (let [card-1 (new-card)
        card-2 (new-card)
        cards  [card-1 card-2]
        points (points-cards cards)]
    {:player-name player-name
     :cards       cards
     :points      points}))

(defn more-cards [player]
  (let [card (new-card)
        cards (conj (:cards player) card)
        new-player (update player :cards conj card)
        points (points-cards cards)]
    (assoc new-player :points points)))

(defn game [player]
  (println (:player-name player) "deseja mais uma carta?")
  (if (= (read-line) "sim")
    (let [player-with-more-cards (more-cards player)]
      (card/print-player player-with-more-cards)
      (game player-with-more-cards))
    player))

(def player-1 (player "Milena"))
(card/print-player player-1)

(def dealer (player "Dealer"))
(card/print-player dealer)

(game player-1)
(game dealer)



;(card/print-player (player "Milena"))
;(card/print-player (player "Dealer"))

