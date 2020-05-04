<template>

  <div class="content">

    <div class="columns">

      <div class="column is-half text-content">

        <h1 class="title"> Welcome to Aion Shard </h1>
        <p> Serveur ... Lorem ipsum dolor sit amet, consectetur adipiscing elit. Curabitur feugiat leo eu turpis
          faucibus
          bibendum. Cras pretium mi eu arcu imperdiet, quis malesuada nisl dignissim. </p>

        <div class="buttons">
          <b-button type="is-primary">Join the server</b-button>
        </div>

      </div>

      <div class="column is-half">
        <Carousel></Carousel>
      </div>
    </div>




  </div>


</template>

<script>

  import Carousel from "~/components/Carousel"
  import Card from "~/components/Card"

  export default {
    name: 'Home',

    components: {
      Carousel,
      Card
    },
  }
</script>

<style>

  .title {
    font-size: 23px;
  }

  .content {
    margin-top: 120px;
  }

  .text-content {
    margin-top: 30px;
  }

  .text-content p {
    margin-top: 35px;
    margin-left: 10px;
    margin-right: 20px;
    text-justify: auto;
  }


  .button {
    margin-top: 15px;
    margin-left: 15px;
  }

</style>
