(ns hero-project.protocols.storage-hero)

(defprotocol StorageHero
  (read-all!         [storage])
  (put!              [storage update-fn])
  (clear-all!        [storage]))

(def IStorageClient StorageHero)