(ns status-im.ui.screens.communities.styles
  (:require
   [quo2.foundations.colors :as quo2.colors]))

(def category-item
  {:flex           1
   :flex-direction :row
   :align-items    :center
   :height         52
   :padding-left   18})

(defn card-redesign [window-width color]
  {:width            window-width
   :background-color color
   :shadow-offset    {:width 0 :height 2}
   :shadow-radius    20
   :shadow-opacity   1
   :shadow-color     "rgba(9, 16, 28, 0.04)"
   :border-radius    20
   :justify-content  :space-between
   :elevation        2})

(defn stats-container []
  {:flex-direction :row
   :align-items    :center
   :margin-right   16})

(defn community-tags-container  []
  {:flex-direction :row
   :position  :absolute
   :top       154
   :left      12
   :right     12})

(defn community-status-container [color]
  {:flex-direction   :row
   :border-radius    200
   :width            48
   :align-items      :center
   :justify-content  :flex-end
   :position         :absolute
   :top              8
   :right            8
   :padding          2
   :background-color color})

(defn community-card-content-container [color]
  {:flex               1
   :position           :absolute
   :top                40
   :left               0
   :right              0
   :bottom             0
   :border-radius      16
   :padding-horizontal 12
   :background-color   color})

(defn community-card-chat-icon [color]
  {:border-radius    32
   :position         :absolute
   :top              -20
   :left             12
   :padding          2
   :background-color color})

(defn community-title-description-container []
  {:position  :absolute
   :top       32
   :left      12
   :right     12})

(defn community-cover-image-container []
  {:height                  64
   :border-top-right-radius 20
   :border-top-left-radius  20
   :background-color        quo2.colors/primary-50-opa-20})