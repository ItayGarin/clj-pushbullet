(ns clj-pushbullet.api
  (:require [cheshire.core :refer [parse-string]]
            [clj-http.client :as client]
            [clj-pushbullet.api-key :refer [api-key]]))

(def ^:private api-url "https://api.pushbullet.com/v2")

(let [params {:type "note"
              :title "Clojure Push!"
              :body "I am sending this from Clojure"}
      url (str api-url "/pushes")]
  (client/request {:method :post
                   :url url
                   :headers {"Access-Token" api-key}
                   :form-params params
                   :as :json
                   :content-type :json}))

(def devices
  (let [url (str api-url "/devices")]
    (client/request {:method :get
                     :url url
                     :headers {"Access-Token" api-key}
                     :as :json
                     :content-type :json})))

(filter identity
        (map :nickname
             (get-in devices [:body :devices])))
