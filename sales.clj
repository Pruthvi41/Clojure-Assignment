(ns sales
  (:gen-class))
(def productData)
(def customerData)
(def salesData)
(def map1 {})
(def map2 {})
(def map4 {})
(def map5 {})
(def temp1 {})
(def temp2 {})
(def temp3 {})
(def productTotal 0)
(def salesTotal 0)

(defn customersDisplay []
  (with-open [rdr (clojure.java.io/reader "cust.txt")]
    (doseq [line (line-seq rdr)]
      (def customerData (clojure.string/split line #"\|"))
      ;(def x (get customerData 0))
      (def y (str ":[" (get customerData 1) ", " (get customerData 2) ", " (get customerData 3) "]"))
      ;(def map1 ( x y))
      ;(temp)
      ;(println map1)
      (def map1 (assoc map1 (Integer/parseInt(get customerData 0)) y))
      (def temp1 (into (sorted-map)map1))
      ))
  ;(println temp1)
  (doseq [[k v] (map vector(keys temp1) (vals temp1))]
    (println (str k " " v)))
  )

(defn productDisplay []
  (with-open [rdr (clojure.java.io/reader "prod.txt")]
    (doseq [line1 (line-seq rdr)]
      (def productData (clojure.string/split line1 #"\|"))
      ;(def x1 (get productData 0))
      ;(def y1 (str (get productData 1) ", " (get productData 2)))
      (def y1 (str ":[" (get productData 1) ", " (get productData 2) "]"))
      (def map2 (assoc map2 (Integer/parseInt(get productData 0)) y1))
      (def temp2 (into (sorted-map)map2))
      ;(print x1)
      ;(println y1)
      )
    )
  (doseq [[k v] (map vector(keys temp2) (vals temp2))]
    (println (str k " " v)))
  )

(defn salesDisplay []
  (with-open [rdr (clojure.java.io/reader "cust.txt")]
    (doseq [line1 (line-seq rdr)]
      (def customerData (clojure.string/split line1 #"\|"))
      (def x1 (str (get customerData 1) ))
      (def map1 (assoc map1 (get customerData 0) x1))
      ))
  (with-open [rdr (clojure.java.io/reader "prod.txt")]
    (doseq [line2 (line-seq rdr)]
      (def productData (clojure.string/split line2 #"\|"))
      (def x2 (str (get productData 1) ))
      (def map4 (assoc map4 (get productData 0) x2))
      ))
  (with-open [rdr (clojure.java.io/reader "sales.txt")]
    (doseq [line3 (line-seq rdr)]
      (def salesData (clojure.string/split line3 #"\|"))
      (if (contains? map1 (get salesData 1))
        (if (contains? map4 (get salesData 2))
          (do
            (def y2 (str ":["(get map1 (get salesData 1))"," (get map4 (get salesData 2))"," (get salesData 3)"]"))
            (def map5 (assoc map5 (Integer/parseInt(get salesData 0)) y2))
            (def temp3 (into (sorted-map)map5)))
          ;(println (get salesData 0)":["(get map1 (get salesData 1))"," (get map4 (get salesData 2))"," (get salesData 3)"]" )
          ))
      ))
  (doseq [[k v] (map vector(keys temp3) (vals temp3))]
    (println (str k " " v)))
  )

(defn customerTotalSales []
  (def salesTotal 0)
  (with-open [rdr (clojure.java.io/reader "cust.txt")]
    (doseq [line1 (line-seq rdr)]
      (def customerData (clojure.string/split line1 #"\|"))
      (def x1 (str (get customerData 0) ))
      (def map1 (assoc map1 (get customerData 1) x1))
      ))

  ;(println map4)
  (println "Enter customer name")
  (let [name (read-line)]
    (if (contains? map1 name)
      (do (with-open [rdr (clojure.java.io/reader "sales.txt")]
            (doseq [line3 (line-seq rdr)]
              (def salesData (clojure.string/split line3 #"\|"))
              (if (= (get salesData 1) (get map1 name))
                ;(println "Hi")
                (with-open [rdr (clojure.java.io/reader "prod.txt")]
                  (doseq [line2 (line-seq rdr)]
                    (def productData (clojure.string/split line2 #"\|"))
                    (if (= (get salesData 2) (get productData 0))
                      (def salesTotal (+ salesTotal (* (Integer/parseInt (get salesData 3)) (Double/parseDouble (get productData 2)))))
                      )

                    ))

                )
              )
            (println name":$" salesTotal)
            )true)
      (do (println "Please enter valid name")false)
      )
    ))
(def map6 {})
(defn productTotalSales []
  (def productTotal 0)

  (with-open [rdr (clojure.java.io/reader "prod.txt")]
    (doseq [line2 (line-seq rdr)]
      (def productData (clojure.string/split line2 #"\|"))
      (def x2 (str (get productData 0) ))
      (def map4 (assoc map4 (get productData 1) x2))
      ))
  ;(println vals map4)

  (println "Enter product name")
  (let [product (read-line)]
    (if (contains? map4 product)
      (do(with-open [rdr (clojure.java.io/reader "sales.txt")]
           (doseq [line3 (line-seq rdr)]
             (def salesData (clojure.string/split line3 #"\|"))
             (if (= (Integer/parseInt (get salesData 2)) (Integer/parseInt (get map4 product)))
               ;(println (get salesData 2))
               (def productTotal (+ productTotal (Integer/parseInt (get salesData 3))))
               )
             ))
         (println product":" productTotal)true)
      (do (println "Provide valid name")false))

    )
  )


(defn exit []
  (println "Good Bye")
  (System/exit 0))
(defn menuDisplay []
  (def menu (str "\n*** Sales Menu ***\n------------------\n1. Display Customer Table\n2. Display Product Table\n3. Display Sales Table\n4. Total Sales for Customer\n5. Total Count for Product\n6. Exit\n\nEnter an option?"))
  (println menu)
  (let [option (read-line)]
    (case option
      "1" (customersDisplay)
      "2" (productDisplay)
      "3" (salesDisplay)
      "4" (customerTotalSales)
      "5" (productTotalSales)
      "6" (exit)
      (println "Please select right choice")))
  (menuDisplay)
  )
(menuDisplay)