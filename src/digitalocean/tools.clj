(ns digitalocean.tools
  (:require [digitalocean.v1.droplet :refer [shutdown-droplet new-droplet snapshot-droplet destroy-droplet]])
  (:require [digitalocean.v1.images :refer [all-images destroy-image]])
  (:require [digitalocean.v1.events :refer [event]])

  )

(def client-id "4a0e8e97e2816868f9a0ed113314a1ad")
(def api-key "e051dc1eb3318ebbe1936f1afc18f75d")


(defn freeze
  [client-id api-key droplet-id image-name]
  (println (shutdown-droplet client-id api-key droplet-id))
  (Thread/sleep 10000)

  (let [imgs (filter #(= (:name %) image-name) (all-images client-id api-key))]
    (println (doseq [img imgs] (destroy-image client-id api-key (:id img))))
    )
  (Thread/sleep 10000)

  (println (shutdown-droplet client-id api-key droplet-id))
  (Thread/sleep 10000)

  (println (snapshot-droplet client-id api-key droplet-id image-name ))
  (Thread/sleep 10000)


  ;(println (destroy-droplet client-id api-key droplet-id))

)

(defn unfreeze
  [client-id api-key droplet-name image-id]
  (new-droplet client-id api-key {:name droplet-name :size_id "66" :image_id image-id :region_id "5" :ssh_key_ids "42550"})
  )


