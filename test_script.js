/*

This is a k6 test script that imports the xk6-kafka and
tests Kafka with a 200 JSON messages per iteration.

*/

import {check} from "k6";
import {uuidv4} from 'https://jslib.k6.io/k6-utils/1.4.0/index.js';
// import * as kafka from "k6/x/kafka";
import {
  Writer,
  Connection,
  CODEC_SNAPPY,
  SCHEMA_TYPE_STRING,
  SCHEMA_TYPE_JSON,
  SchemaRegistry
} from "k6/x/kafka"; // import kafka extension

// Prints module-level constants
// console.log(kafka);

const brokers = ["localhost:19092"];
const topic = "events";

const writer = new Writer({
  brokers: brokers,
  topic: topic,
  compression: CODEC_SNAPPY,
});
const connection = new Connection({
  address: brokers[0],
});
const schemaRegistry = new SchemaRegistry();

export const options = {
  thresholds: {
    // Base thresholds to see if the writer is working
    kafka_writer_error_count: ["count == 0"],
  },
  scenarios: {
    default: {
      executor: 'per-vu-iterations',
      vus: 10,
      iterations: 1000,
      maxDuration: '3m'
    }
  }
};

export default function () {
  const messages = [
    {
      key: schemaRegistry.serialize({
        data: uuidv4(),
        schemaType: SCHEMA_TYPE_STRING,
      }),
      value: schemaRegistry.serialize({
        data: {
          "id": uuidv4(),
          "payload": {
            "data": "data"
          }
        },
        schemaType: SCHEMA_TYPE_JSON
      }),
    },
  ];

  const error = writer.produce({messages: messages});
  check(error, {
    'is sent': (err) => typeof err === 'undefined',
  });
}

export function teardown(data) {
  writer.close();
  connection.close();
}
