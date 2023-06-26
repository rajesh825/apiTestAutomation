```
import fs from 'fs';
import yaml from 'js-yaml';

type Value = string | string[];  // This is the type for propertyValue now

const doc: SauceConfig = yaml.load(fs.readFileSync(ymlconfiguration,'utf8')) as SauceConfig;

export function updateConfig(propertyToUpdate: string, propertyValue: Value){
    // Do nothing if propertyValue is empty or null
    if (!propertyValue || (Array.isArray(propertyValue) && propertyValue.length === 0)) {
        console.log(`propertyValue is empty or null, no update performed.`);
        return;
    }

    if (propertyToUpdate in doc.suites[0].env) {
        (doc.suites[0].env as any)[propertyToUpdate] = propertyValue;
    } else if (propertyToUpdate === 'metadata.build') {
        doc.sauce.metadata.build = propertyValue as string;  // We assume that build should always be a string
    } else if (propertyToUpdate === 'metadata.tags') {
        doc.sauce.metadata.tags = propertyValue as string[];  // We assume that tags should always be an array of strings
    } else {
        console.error('Invalid property to update');
    }
}






