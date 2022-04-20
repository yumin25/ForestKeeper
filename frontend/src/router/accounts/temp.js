const uploadFile = (e) => {
    e.stopPropagation();
    let reader = new FileReader();
    let file = e.target.files[0];

      const filesInArr = Array.from(e.target.files);

      reader.onloadend = () => {
        setPostfiles({
          file: filesInArr,
          previewURL: reader.result,
        });
      };

      if (file) {
        reader.readAsDataURL(file);

  };