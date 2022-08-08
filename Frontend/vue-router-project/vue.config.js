const { defineConfig } = require("@vue/cli-service");
module.exports = defineConfig({
  transpileDependencies: true,
  devServer: {
    port: 8081,
  },
  css: {
    loaderOptions: {
      sass: {
        additionalData: `
          @import "@/assets/style.scss";
          `,
      },
    },
  },
});
