{
 "cells": [
  {
   "cell_type": "code",
   "execution_count": 1,
   "metadata": {
    "colab": {
     "base_uri": "https://localhost:8080/",
     "height": 139
    },
    "colab_type": "code",
    "id": "J3F5ONCyrxgK",
    "outputId": "19fe063e-d6eb-4a8a-b9ee-17633f67e941"
   },
   "outputs": [
    {
     "data": {
      "text/plain": [
       "'2.0.0'"
      ]
     },
     "execution_count": 1,
     "metadata": {},
     "output_type": "execute_result"
    }
   ],
   "source": [
    "import tensorflow as tf\n",
    "import numpy as np\n",
    "\n",
    "tf.__version__"
   ]
  },
  {
   "cell_type": "code",
   "execution_count": 20,
   "metadata": {
    "colab": {},
    "colab_type": "code",
    "id": "75SgzlnaUhv_"
   },
   "outputs": [],
   "source": [
    "def load_and_preprocess_image(image_path):\n",
    "    test_input_image = tf.keras.preprocessing.image.load_img(image_path)\n",
    "    test_input_image = tf.keras.preprocessing.image.img_to_array(test_input_image)\n",
    "    test_input_image = tf.image.resize(test_input_image, [257, 257])\n",
    "    test_input_image = tf.cast(test_input_image, np.float32) / 255.0\n",
    "    test_input_image = test_input_image[None, ...]\n",
    "#     test_input_image = tf.image.rot90(test_input_image, k=3)\n",
    "    return test_input_image"
   ]
  },
  {
   "cell_type": "code",
   "execution_count": 25,
   "metadata": {
    "colab": {},
    "colab_type": "code",
    "id": "qY0mMYlioQWa"
   },
   "outputs": [],
   "source": [
    "interpreter = tf.lite.Interpreter(model_path='deeplabv3_257_mv_gpu.tflite')\n",
    "interpreter.allocate_tensors()\n",
    "input_data = interpreter.get_input_details()[0]\n",
    "output = interpreter.get_output_details()[0]\n",
    "output = interpreter.get_tensor(output['index'])\n",
    "output = output[0]\n",
    "\n",
    "image = None"
   ]
  },
  {
   "cell_type": "code",
   "execution_count": 25,
   "metadata": {
    "colab": {},
    "colab_type": "code",
    "id": "qY0mMYlioQWa"
   },
   "outputs": [],
   "source": [
    "def no_background(image_path):\n",
    "    global image\n",
    "    global interpreter\n",
    "    global output\n",
    "    image = load_and_preprocess_image(image_path)\n",
    "    \n",
    "    interpreter.set_tensor(input_data['index'], image)\n",
    "    interpreter.invoke()\n",
    "\n",
    "    final = np.array([], dtype=np.float32)\n",
    "    final = np.resize(final, (257, 257, 4))\n",
    "\n",
    "    for i in range(0, 257):\n",
    "        for j in range(0, 257):\n",
    "            k = np.argmax(output[i][j])\n",
    "            if (k == 15):\n",
    "                final[i][j] = [image[0][i][j][0], image[0][i][j][1], image[0][i][j][2], 1.0]\n",
    "            else:\n",
    "                final[i][j] = [image[0][i][j][0], image[0][i][j][1], image[0][i][j][2], 0.0]\n",
    "\n",
    "    result_image = tf.keras.preprocessing.image.array_to_img(final)\n",
    "\n",
    "    return result_image"
   ]
  },
  {
   "cell_type": "code",
   "execution_count": null,
   "metadata": {
    "colab": {
     "base_uri": "https://localhost:8080/",
     "height": 274
    },
    "colab_type": "code",
    "id": "jZHa8Be24swt",
    "outputId": "863c220a-e218-4796-fabf-1e1dc0fd57b6"
   },
   "outputs": [],
   "source": [
    "result_image = no_background('your_path')\n",
    "# result_image"
   ]
  },
  {
   "cell_type": "code",
   "execution_count": 7,
   "metadata": {
    "colab": {},
    "colab_type": "code",
    "id": "DBmDn6Rq7VUz"
   },
   "outputs": [],
   "source": [
    "tf.keras.preprocessing.image.save_img('path',\n",
    "                                      result_image)"
   ]
  }
 ],
 "metadata": {
  "accelerator": "GPU",
  "colab": {
   "collapsed_sections": [],
   "name": "TFLite.ipynb",
   "provenance": []
  },
  "kernelspec": {
   "display_name": "Python 3",
   "language": "python",
   "name": "python3"
  },
  "language_info": {
   "codemirror_mode": {
    "name": "ipython",
    "version": 3
   },
   "file_extension": ".py",
   "mimetype": "text/x-python",
   "name": "python",
   "nbconvert_exporter": "python",
   "pygments_lexer": "ipython3",
   "version": "3.7.3"
  }
 },
 "nbformat": 4,
 "nbformat_minor": 4
}
