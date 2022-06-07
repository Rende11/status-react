(ns status-im.ui.screens.communities.community-views-redesign
  (:require
   [quo2.components.text :as text]
   [status-im.i18n.i18n :as i18n]
   [status-im.communities.core :as communities]
   [status-im.utils.handlers :refer [>evt <sub]]
   [status-im.ui.components.react :as react]
   [status-im.ui.screens.communities.community :as community]
   [status-im.ui.screens.communities.icon :as communities.icon]
   [status-im.ui.screens.communities.styles :as styles]
   [quo2.components.filter-tag :as quo2.filter-tag]
   [quo2.foundations.typography :as typography]
   [quo2.components.icon :as icons]
   [quo2.foundations.colors :as quo2.colors]))

(defn stats-column []
  [react/view {:flex-direction    :row
               :position  :absolute
               :top       116
               :left      12
               :right     12}
   [react/view {:style (styles/stats-container)}
    [icons/icon :main-icons2/group16 {:container-style {:align-items     :center
                                                        :justify-content :center}
                                      :resize-mode      :center
                                      :size             16
                                      :color            (quo2.colors/theme-colors
                                                         quo2.colors/neutral-50
                                                         quo2.colors/neutral-40)}]
    [text/text {:style (merge typography/font-regular
                              typography/paragraph-1)}
     (i18n/format-members 630000)]]
   [react/view {:style (styles/stats-container)}
    [icons/icon :main-icons2/lightning16 {:container-style {:align-items     :center
                                                            :justify-content :center}
                                          :resize-mode      :center
                                          :size             16
                                          :color            (quo2.colors/theme-colors
                                                             quo2.colors/neutral-50
                                                             quo2.colors/neutral-40)}]
    [text/text {:style (merge typography/font-regular
                              typography/paragraph-1)}
     (i18n/format-members 3300)]]
   [react/view {:style (styles/stats-container)}
    [icons/icon :main-icons2/placeholder16 {:container-style {:align-items     :center
                                                              :justify-content :center}
                                            :resize-mode      :center
                                            :color            (quo2.colors/theme-colors
                                                               quo2.colors/neutral-50
                                                               quo2.colors/neutral-40)}]
    [text/text {:style (merge typography/font-regular
                              typography/paragraph-1)}
     (i18n/format-members 63)]]])

(defn community-tags [tags]
  [react/view {:style (styles/community-tags-container)}
   (for [{:keys [id label resource]} tags]
     ^{:key id}
     [react/view {:margin-right 8}
      [quo2.filter-tag/tag
       {:id       id
        :size     24
        :resource resource}
       label]])])

(defn comuninity-status [permissions color community status]
  (when (= status "gated")
    [react/view {:style (styles/community-status-container color)}
     [icons/icon (if permissions :main-icons2/unlocked16 :main-icons2/locked16)
      {:container-style {:align-items      :center
                         :justify-content  :center}
       :resize-mode      :center
       :color            (quo2.colors/theme-colors
                          quo2.colors/neutral-50
                          quo2.colors/neutral-40)}]
     [communities.icon/community-icon-redesign community 20]]))

(defn community-card-list-item [{:keys [id name description members permissions
                                        status tags section] :as community}]
  (let [status-bg-color     (quo2.colors/theme-colors quo2.colors/neutral-10 quo2.colors/neutral-80)
        theme-color         (quo2.colors/theme-colors quo2.colors/white quo2.colors/neutral-90)
        window-width (* (<sub [:dimensions/window-width]) 0.91)]
    [react/view {:style (merge (styles/card-redesign window-width theme-color)
                               {:margin-bottom  12}
                               (if (= section "featured")
                                 {:margin-right       12
                                  :width              window-width}
                                 {:margin-bottom      16}))}
     [react/touchable-opacity {:style         (merge {:height               230
                                                      :border-radius        20})
                               :on-press      (fn []
                                                (>evt [::communities/load-category-states id])
                                                (>evt [:dismiss-keyboard])
                                                (>evt [:navigate-to :community {:community-id id}]))
                               :on-long-press #(>evt [:bottom-sheet/show-sheet
                                                      {:content (fn []
                                                                  [community/community-actions community])}])}
      [:<>
       [react/view {:style (styles/community-cover-image-container)}]
       [react/view {:style (styles/community-card-content-container theme-color)}
        [react/view {:style (styles/community-card-chat-icon theme-color)}
         [communities.icon/community-icon-redesign community 48]]
        [comuninity-status permissions status-bg-color community status]
        [react/view {:style (styles/community-title-description-container)}
         [text/text
          {:style (merge
                   typography/font-semi-bold
                   typography/heading-2)
           :accessibility-label :chat-name-text
           :number-of-lines     1
           :ellipsize-mode      :tail}
          name]
         [text/text
          {:style (merge typography/font-regular
                         typography/paragraph-1)
           :accessibility-label :community-description-text
           :number-of-lines     2
           :ellipsize-mode      :tail}
          description]]
        [stats-column members]
        [community-tags tags]]]]]))

(defn categorized-communities-list-item [{:keys [id name members status] :as community}]
  (let [card-bg-color (quo2.colors/theme-colors quo2.colors/white quo2.colors/neutral-90)
        status-bg-color (quo2.colors/theme-colors quo2.colors/neutral-10 quo2.colors/neutral-80)
        permissions  false
        window-width (* (<sub [:dimensions/window-width]) 0.91)]
    [react/view {:style (merge (styles/card-redesign window-width card-bg-color)
                               {:margin-bottom  12})}
     [react/touchable-opacity {:border-radius        20
                               :on-press      (fn []
                                                (>evt [::communities/load-category-states id])
                                                (>evt [:dismiss-keyboard])
                                                (>evt [:navigate-to :community {:community-id id}]))
                               :on-long-press #(>evt [:bottom-sheet/show-sheet
                                                      {:content (fn []
                                                                  [community/community-actions community])}])}
      [react/view {:flex               1}
       [react/view {:flex-direction    :row
                    :border-radius      16
                    :align-items       :center
                    :background-color   card-bg-color}
        [react/view {:border-radius    32
                     :padding          12
                     :background-color card-bg-color}
         [communities.icon/community-icon-redesign community 48]]
        [react/view
         [text/text
          {:style (merge
                   typography/font-semi-bold
                   typography/heading-2)
           :accessibility-label :chat-name-text
           :number-of-lines     1
           :ellipsize-mode      :tail}
          name]
         [react/view {:flex-direction    :row
                      :align-items       :center}
          [stats-column members]]]
        [comuninity-status permissions status-bg-color community status]]]]]))




