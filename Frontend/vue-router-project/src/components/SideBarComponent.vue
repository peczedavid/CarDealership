<template>
  <div class="felx-shrink-0 p-4 bg-white" style="width: 350px">
    <ul class="list-unstyled">
      <h2 class="">Filter</h2>
      <li class="border-top my-3"></li>
    </ul>
    <form>
      <button class="btn btn-outline-primary mb-2" type="submit">Search</button>
      <div class="form-group row">
        <label for="inputBrand" class="me-2 col-sm-2 col-form-label">Brand:</label>
        <div class="col-sm-9">
          <input type="text" class="form-control" id="inputBrand">
        </div>
      </div>
      <div class="form-group row">
        <label for="inputModel" class="me-2 col-sm-2 col-form-label">Model:</label>
        <div class="col-sm-9">
          <input type="text" class="form-control" id="inputModel">
        </div>
      </div>
      <div class="form-group row">
        <label for="inputRegion" class="me-2 col-sm-2 col-form-label">Region:</label>
        <div class="col-sm-9">
          <select class="mt-2" id="regionSelect">
            <option value="" disabled selected>Select your region</option>
            <!--TODO: ask from the database the available regions-->
            <option value="america">America</option>
            <option value="germany">Germany</option>
            <option value="japan">Japan</option>
            <option value="">All</option>
          </select>
        </div>
        <!-- <label for="inputRegion" class="me-2 col-sm-2 col-form-label">Region:</label>
        <div class="col-sm-9">
          <input type="text" class="form-control" id="inputRegion" placeholder="America">
        </div> -->
      </div>
    </form>
  </div>
</template>

<script>
export default {};
</script>

<style>
</style>
