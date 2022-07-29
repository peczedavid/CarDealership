<template>
  <div class="hello">
    <h1>Hello Vue</h1>
    <p>{{ contentString }}</p>
    <p>{{ adminContentString }}</p>
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
      adminContentString: "no admin content",
      car: {
        brand: "default brand",
        model: "default model",
        region: "default region"
      }
    };
  },
  methods: {
    getCarTest() {
      TestService.getCarById()
       .then(response => {
         this.car.brand = response.data.brand;
         this.car.model = response.data.model;
         this.car.region = response.data.regions[0].name;
          console.log(response.data);
        })
        .catch(e => {
          console.log(e);
        });
    },
    async signInAdmin() {
      this.contentString = await TestService.signInAdmin();
    },
    async getAdminContent() {
      this.adminContentString = await TestService.getAdminContent();
    }
  },
  async mounted() {
    await this.signInAdmin();
    //this.getContentString();
    this.getCarTest();

    await this.getAdminContent();
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