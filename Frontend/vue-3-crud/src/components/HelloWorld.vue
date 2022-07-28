<template>
  <div class="hello">
    <h1>Hello Vue</h1>
    <p>{{ contentString }}</p>
    <p>{{ car.brand }}</p>
    <p>{{ car.model }}</p>
    <p>{{ car.region }}</p>
  </div>
</template>

<script>
import TestService from "../services/TestService";

export default {
  name: 'HelloWorld',
  props: {
    msg: String
  },
  data() {
    return {
      contentString: "no content",
      car: {
        brand: "default name",
        model: "default model",
        region: "default region"
      }
    };
  },
  methods: {
    getContentString() {
      TestService.getPublicContent()
        .then(response => {
          this.contentString = response.data;
          console.log(response.data);
        })
        .catch(e => {
          console.log(e);
        });
    },
    getCarTest() {
      TestService.signInAdmin();

      TestService.getCarById()
       .then(response => {
          this.contentString = response.data;
          console.log(response.data);
        })
        .catch(e => {
          console.log(e);
        });
    }
  },
  mounted() {
    this.getContentString();
    this.getCarTest();
  }
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
h3 {
  margin: 40px 0 0;
}
ul {
  list-style-type: none;
  padding: 0;
}
li {
  display: inline-block;
  margin: 0 10px;
}
a {
  color: #42b983;
}
</style>