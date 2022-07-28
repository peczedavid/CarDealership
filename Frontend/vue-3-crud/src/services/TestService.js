import http from "../http-common";
class TestService {
  getPublicContent() {
    return http.get("/test/all");
  }
  getCarById() {
    return http.get("/cars/getById/4");
  }
  async signInAdmin() {
    let payload = {
      username: "admin",
      password: "admin"
    };
    let res = await http.post("/auth/signin", payload);

    let data = res.data;
    console.log(data);
  }
}
export default new TestService();