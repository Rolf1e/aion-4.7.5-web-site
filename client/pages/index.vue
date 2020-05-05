<template>

  <div class="content">

    <div class="columns">

      <div class="column is-half text-content">

        <h1 class="title"> Welcome to Aion Shard </h1>
        <p> Come and discover a server with a multitude of custom features.<br>
          A pvp energized and encouraged, pve instances reworked so that you can rediscover the feeling of novelty. A team listening to you and much more!</p>

        <div class="buttons">
          <b-button type="is-primary">Join the server</b-button>
        </div>

      </div>

      <!--<div class="column is-half">
        <Carousel></Carousel>
      </div>-->
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
  .content .columns {
    margin-bottom: 5%;
    margin-left: 1%;
  }

  .content .columns .title {
    font-size: 250%;
    color: #f7f8fb;
  }

  .content {
    margin-top: 10%;
  }

  .text-content {
    margin-top: 5%;
  }

  .text-content p {
    margin-top: 5%;
    text-justify: auto;
    color: #f7f8fb;
  }

  .text-content .buttons {
    margin-top: 5%;
  }

</style>
