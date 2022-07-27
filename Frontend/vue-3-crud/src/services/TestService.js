import http from "../http-common";
class TestService {
  getPublicContent() {
    return http.get("/test/all");
  }
}
export default new TestService();