# -*- coding: utf-8 -*-

import tensorflow as tf
import numpy as np

interpreter = tf.lite.Interpreter(model_path='deeplabv3_257_mv_gpu.tflite')
interpreter.allocate_tensors()
input_data = interpreter.get_input_details()[0]
output = interpreter.get_output_details()[0]
output = interpreter.get_tensor(output['index'])
output = output[0]

image = None


def load_and_preprocess_image(image_path):
    test_input_image = tf.keras.preprocessing.image.load_img(image_path)
    test_input_image = tf.keras.preprocessing.image.img_to_array(test_input_image)
    test_input_image = tf.image.resize(test_input_image, [257, 257])
    test_input_image = tf.cast(test_input_image, np.float32) / 255.0
    test_input_image = test_input_image[None, ...]
    # test_input_image = tf.image.rot90(test_input_image, k=3)
    return test_input_image


def no_background(image_path):
    global image
    global interpreter
    global output
    image = load_and_preprocess_image(image_path)

    interpreter.set_tensor(input_data['index'], image)
    interpreter.invoke()

    final = np.array([], dtype=np.float32)
    final = np.resize(final, (257, 257, 4))

    for i in range(0, 257):
        for j in range(0, 257):
            k = np.argmax(output[i][j])
            if k == 15:
                final[i][j] = [image[0][i][j][0], image[0][i][j][1], image[0][i][j][2], 1.0]
            else:
                final[i][j] = [image[0][i][j][0], image[0][i][j][1], image[0][i][j][2], 0.0]

    result_image = tf.keras.preprocessing.image.array_to_img(final)
    tf.keras.preprocessing.image.save_img('image.png', result_image)


if __name__ == '__main__':
    no_background(path)
