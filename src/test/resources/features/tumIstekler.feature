Feature: API Get, Post, Put, Delete Requests

  Scenario:Tum Request'ler
    Given Kullanici olusturulur
    And Kullanicinin verileri alinir "users_endpoint"
    And Kullanicinin bilgileri "users_endpoint" endpointi kullanilarak guncellenir "ROLE_MANAGER"
    And Kullanicinin verileri alinir "users_endpoint"
    And Kullanici silinir
    And Kullanicinin verileri alinir "users_endpoint"
