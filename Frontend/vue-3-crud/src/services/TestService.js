import http from "../http-common";
class TestService {
  getPublicContent() {
    return http.get("/test/all");
  }
  async getCarById() {
    return await http.get("/cars/getById/4");
  }
  async signInAdmin() {
    let payload = {
      username: "admin",
      password: "admin"
    };
    let res = await http.post("/auth/signin", payload, { withCredentials: true});

    let data = res.data;
    return data;//console.log(data);
  }

  async getAdminContent() {
    let res = await http.get("test/admin", { withCredentials: true});
    let data = res.data;
    return data;//console.log(data);
  }
}
export default new TestService();