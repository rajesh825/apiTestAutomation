```
import fs from 'fs';
import yaml from 'js-yaml';

export async function updateSauceConfig(propertyToUpdate: string, propertyValue: string | string[]): Promise<void> {
    try {
        // read the yaml file
        let file: string = fs.readFileSync('./path/to/your/config.yaml', 'utf8');

        // convert the yaml file to javascript object
        let config: any = yaml.load(file);

        // update the property in the config object
        if (Array.isArray(propertyValue)) {
            if (Array.isArray(config[propertyToUpdate])) {
                // if the property value is an array and the existing property value is also an array, merge them
                config[propertyToUpdate] = [...config[propertyToUpdate], ...propertyValue];
            } else {
                // if the property value is an array but the existing property value is not an array, replace it
                config[propertyToUpdate] = propertyValue;
            }
        } else {
            // if the property value is not an array, replace the existing property value
            config[propertyToUpdate] = propertyValue;
        }

        // convert the javascript object back to yaml
        let yamlStr: string = yaml.dump(config);

        // write the updated config back to the file
        fs.writeFileSync('./path/to/your/config.yaml', yamlStr, 'utf8');
        
    } catch(e) {
        console.log(e);
    }
}
